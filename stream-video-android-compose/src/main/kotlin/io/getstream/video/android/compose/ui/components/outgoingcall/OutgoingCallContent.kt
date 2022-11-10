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

package io.getstream.video.android.compose.ui.components.outgoingcall

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.CallTopAppbar
import io.getstream.video.android.compose.ui.components.background.CallBackground
import io.getstream.video.android.compose.ui.components.mock.mockParticipant
import io.getstream.video.android.model.CallType
import io.getstream.video.android.model.CallUser
import io.getstream.video.android.viewmodel.OutgoingCallViewModel

@Composable
public fun OutgoingCallScreen(
    viewModel: OutgoingCallViewModel,
    onCancelCall: (String) -> Unit = {},
    onMicToggleChanged: (Boolean) -> Unit = {},
    onVideoToggleChanged: (Boolean) -> Unit = {},
) {
    val callType: CallType by viewModel.callType.collectAsState()
    val callId: String by viewModel.callId.collectAsState()
    val participants: List<CallUser> by viewModel.participants.collectAsState()
    OutgoingCall(
        callType = callType,
        callId = callId,
        participants = participants,
        onCancelCall = onCancelCall,
        onMicToggleChanged = onMicToggleChanged,
        onVideoToggleChanged = onVideoToggleChanged
    )
}

@Composable
public fun OutgoingCall(
    callType: CallType,
    callId: String,
    participants: List<CallUser>,
    onCancelCall: (String) -> Unit = {},
    onMicToggleChanged: (Boolean) -> Unit = {},
    onVideoToggleChanged: (Boolean) -> Unit = {},
) {

    CallBackground(
        participants = participants,
        callType = callType,
        isIncoming = false
    ) {

        Column {

            CallTopAppbar()

            val topPadding = if (participants.size == 1 || callType == CallType.VIDEO) {
                VideoTheme.dimens.singleAvatarAppbarPadding
            } else {
                VideoTheme.dimens.avatarAppbarPadding
            }

            OutgoingCallDetails(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = topPadding),
                participants = participants,
                callType = callType
            )
        }

        if (participants.size == 1) {
            OutgoingSingleCallOptions(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 44.dp),
                callId = callId,
                onCancelCall = onCancelCall,
                onMicToggleChanged = onMicToggleChanged,
                onVideoToggleChanged = onVideoToggleChanged
            )
        } else {
            OutgoingGroupCallOptions(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 44.dp),
                callId = callId,
                onCancelCall = onCancelCall,
                onMicToggleChanged = onMicToggleChanged,
                onVideoToggleChanged = onVideoToggleChanged,
            )
        }
    }
}

@Preview
@Composable
private fun OutgoingCallPreview() {
    VideoTheme {
        OutgoingCall(
            callType = CallType.VIDEO,
            callId = "",
            participants = listOf(
                mockParticipant.let {
                    CallUser(it.id, it.name, it.role, it.profileImageURL ?: "", null, null)
                }
            ),
            onCancelCall = { },
            onMicToggleChanged = { },
            onVideoToggleChanged = { },
        )
    }
}
