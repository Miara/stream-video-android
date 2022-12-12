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

package io.getstream.video.android.compose.ui.components.mock

import io.getstream.video.android.model.CallParticipantState

internal val mockParticipant: CallParticipantState
    inline get() = mockUsers[0]

internal val mockParticipantList: List<CallParticipantState>
    inline get() = mockUsers

@PublishedApi
internal val mockUsers: List<CallParticipantState>
    inline get() = listOf(
        CallParticipantState(
            id = "filip_babic",
            name = "Filip",
            profileImageURL = "https://avatars.githubusercontent.com/u/17215808?v=4",
            idPrefix = "",
            role = "",
            sessionId = ""
        ),
        CallParticipantState(
            id = "jaewoong",
            name = "Jaewoong Eum",
            profileImageURL = "https://ca.slack-edge.com/T02RM6X6B-U02HU1XR9LM-626fb91c334e-128",
            idPrefix = "",
            role = "",
            sessionId = ""
        ),
        CallParticipantState(
            id = "toma_zdravkovic",
            name = "Toma Zdravkovic",
            profileImageURL = "https://upload.wikimedia.org/wikipedia/commons/d/da/Toma_Zdravkovi%C4%87.jpg",
            idPrefix = "",
            role = "",
            sessionId = ""
        )
    )
