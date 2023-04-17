/*
 * Copyright (c) 2014-2023 Stream.io Inc. All rights reserved.
 *
 * Licensed under the Stream License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://github.com/GetStream/stream-video-android/blob/main/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.video.android.core

import android.content.Context
import android.net.ConnectivityManager
import io.getstream.video.android.core.dispatchers.DispatcherProvider
import io.getstream.video.android.core.internal.network.NetworkStateProvider
import io.getstream.video.android.core.socket.CoordinatorSocket
import io.getstream.video.android.core.socket.SfuSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.TimeUnit

/**
 * Test coverage for the sockets
 *
 * @see PersistentSocket
 * @see CoordinatorSocket
 * @see SfuSocket
 *
 * The socket does a few things
 * - health check/ping every 30 seconds
 * - monitors network state
 * - in case of temporary errors retry and for permanent ones fail
 *
 * The Sfu and coordinator have slightly different implements
 * Auth receives different events
 *
 * @see JoinCallEvent (for the sfu)
 * @see ConnectedEvent (for the coordinator)
 *
 * The sfu uses binary messages and the coordinator text
 * Other than it's mostly the same logic
 *
 */
@RunWith(RobolectricTestRunner::class)
class SocketTest : TestBase() {
    val coordinatorUrl =
        "https://video.stream-io-api.com/video/connect?api_key=hd8szvscpxvd&stream-auth-type=jwt&X-Stream-Client=stream-video-android"
    val sfuUrl = "https://sfu-039364a.lsw-ams1.stream-io-video.com/ws?api_key=hd8szvscpxvd"

    val sfuToken =
        "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI4ZDBlYjU0NDg4ZDFiYTUxOTk3Y2Y1NWRmYTY0Y2NiMCIsInN1YiI6InVzZXIvdGhpZXJyeSIsImF1ZCI6WyJzZnUtMDM5MzY0YS5sc3ctYW1zMS5zdHJlYW0taW8tdmlkZW8uY29tIl0sImV4cCI6MTY4MTM4MTUzOSwibmJmIjoxNjgxMzU5OTM5LCJpYXQiOjE2ODEzNTk5MzksImFwcF9pZCI6MTEyOTUyOCwiY2FsbF9pZCI6ImRlZmF1bHQ6ZmZlZDM5MDgtYTM0Ni00ZjM5LTg4MWYtZjJkMWNjOGM4YTE5IiwidXNlciI6eyJpZCI6InRoaWVycnkiLCJuYW1lIjoiVGhpZXJyeSIsImltYWdlIjoiaGVsbG8iLCJ0cCI6IjJ0T21iVVZoQVNSdytnaDNFM2hheWJTZEpwdUVwTWk0In0sInJvbGVzIjpbInVzZXIiXSwib3duZXIiOnRydWV9.9MAI0if2Uxc-m7pu56bSPxpoP_Yu8TB-QVd3187NmA4v_O1uoRkWPNV-l-ieTgUoqKsJtncvmgG0Xts0W7nSMw"

    fun buildOkHttp(): OkHttpClient {
        val connectionTimeoutInMs = 10000L
        return OkHttpClient.Builder()
//            .addInterceptor(
//                buildCredentialsInterceptor(
//                    interceptorWrapper = interceptorWrapper
//                )
//            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            // .addInterceptor(buildHostSelectionInterceptor(interceptorWrapper = interceptorWrapper))
            .connectTimeout(connectionTimeoutInMs, TimeUnit.MILLISECONDS)
            .writeTimeout(connectionTimeoutInMs, TimeUnit.MILLISECONDS)
            .readTimeout(connectionTimeoutInMs, TimeUnit.MILLISECONDS)
            .callTimeout(connectionTimeoutInMs, TimeUnit.MILLISECONDS)
            .build()
    }

    @Test
    fun `connect the socket`() = runTest {
        val networkStateProvider = NetworkStateProvider(
            connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
        val scope = CoroutineScope(DispatcherProvider.IO)
        val socket = CoordinatorSocket(coordinatorUrl, testData.users["thierry"]!!, testData.tokens["thierry"]!!, scope, buildOkHttp(), networkStateProvider)

        socket.connect()

        val job = launch {

            socket.events.collect() {
            }
        }

        val job2 = launch {

            socket.errors.collect() {
                throw it
            }
        }

        // wait for the socket to connect (connect response or error)

        socket.disconnect()
        job.cancel()
        job2.cancel()
    }

    @Test
    fun `sfu socket`() = runTest {
        val networkStateProvider = NetworkStateProvider(
            connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
        val sessionId = randomUUID().toString()
        val updateSdp: () -> String = {
            "hello"
        }
        val scope = CoroutineScope(DispatcherProvider.IO)
        val socket = SfuSocket(
            sfuUrl,
            sessionId,
            sfuToken,
            updateSdp,
            scope,
            buildOkHttp(),
            networkStateProvider
        )
        socket.connect()

        launch {

            socket.events.collect() {
            }
        }

        launch {

            socket.errors.collect() {
                throw it
            }
        }
    }

    @Test
    fun `if we get a temporary error we should retry`() = runTest {
        // for instance a network failure
    }

    @Test
    fun `a permanent error shouldn't be retried`() = runTest {
        // for authentication failures
    }

    @Test
    fun `error parsing`() = runTest {
    }
}
