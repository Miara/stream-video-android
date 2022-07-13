/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.video.android.client.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import stream.video.User

public class UserState {

    /**
     * Represents the internal Flow that caches the last user instance and emits it to all
     * subscribers.
     */
    private val userFlow = MutableStateFlow(EMPTY_USER)
    public val user: Flow<User> = userFlow

    /**
     * Emits the new user update to all listeners.
     *
     * @param [user] The new user instance to set.
     */
    public fun setUser(user: User) {
        this.userFlow.value = user
    }

    public companion object {
        private val EMPTY_USER = User()
    }
}
