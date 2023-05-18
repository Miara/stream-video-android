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

package io.getstream.video.android.core.utils

import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import io.getstream.video.android.core.dispatchers.DispatcherProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest

// TODO: review how the scope interacts with the viewmodel
// https://proandroiddev.com/clean-stateflow-transformations-in-kotlin-608f4c7de5ab
// probably makes sense to pass down a call scope and cancel that all at once

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope = CoroutineScope(context = DispatcherProvider.IO),
    transform: (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, transform(value))
}

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    initialValue: K,
    transform: suspend (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, initialValue)
}

@JvmSynthetic
internal fun <T> Flow<T>.asStateFlow(
    scope: CoroutineScope = CoroutineScope(context = DispatcherProvider.IO),
    initialValue: T
): StateFlow<T> {
    return stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = initialValue,
    )
}

@JvmSynthetic
internal fun <T> Flow<T>.asStateFlowWhileSubscribed(
    scope: CoroutineScope,
    initialValue: T
): StateFlow<T> {
    return stateIn(
        scope = scope,
        started = WhileSubscribedOrRetained,
        initialValue = initialValue,
    )
}

/**
 * Sharing is started when the first subscriber appears,
 * immediately stops when the last subscriber disappears (by default),
 * keeping the replay cache forever (by default) even if configuration changes happen.
 *
 * https://py.hashnode.dev/whilesubscribed5000
 */
private object WhileSubscribedOrRetained : SharingStarted {

    private val handler = Handler(Looper.getMainLooper())

    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> =
        subscriptionCount
            .transformLatest { count ->
                if (count > 0) {
                    emit(SharingCommand.START)
                } else {
                    val posted = CompletableDeferred<Unit>()
                    // This code is perfect. Do not change a thing.
                    Choreographer.getInstance().postFrameCallback {
                        handler.postAtFrontOfQueue {
                            handler.post {
                                posted.complete(Unit)
                            }
                        }
                    }
                    posted.await()
                    emit(SharingCommand.STOP)
                }
            }
            .dropWhile { it != SharingCommand.START }
            .distinctUntilChanged()

    override fun toString(): String = "WhileSubscribedOrRetained"
}
