/*
 * Copyright (c) 2014-2022 Stream.io Inc. All rights reserved.
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

package io.getstream.video.android.core.user

import io.getstream.video.android.model.ApiKey
import io.getstream.video.android.model.Device
import io.getstream.video.android.model.User

public interface UserPreferences {

    /**
     * Fetches the cached credentials from SDK persistence.
     *
     * @return The stored [User] credentials if they exist.
     */
    public fun getUserCredentials(): io.getstream.video.android.model.User?

    /**
     * Stores user credentials for the SDK to use. Useful when logging in to the SDK
     *
     * @param user The credentials to store.
     */
    public fun storeUserCredentials(user: io.getstream.video.android.model.User)

    /**
     * Fetches the cached ApiKey from SDK persistence.
     *
     * @return The stored [ApiKey] if it exist.
     */
    public fun getApiKey(): io.getstream.video.android.model.ApiKey

    /**
     * Stores ApiKey for the SDK to use. Useful when logging in to the SDK.
     *
     * @param apiKey The [ApiKey] to store.
     */
    public fun storeApiKey(apiKey: io.getstream.video.android.model.ApiKey)

    public fun storeUserToken(userToken: String)
    public fun getUserToken(): String

    public fun storeDevice(device: io.getstream.video.android.model.Device)

    public fun getDevices(): List<io.getstream.video.android.model.Device>

    public fun removeDevices()

    /**
     * Used to clear the preferences from any credentials that are stored. Useful when logging out.
     */
    public fun clear()
}
