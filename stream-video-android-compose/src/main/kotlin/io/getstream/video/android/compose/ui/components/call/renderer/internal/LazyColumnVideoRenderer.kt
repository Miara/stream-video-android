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

package io.getstream.video.android.compose.ui.components.call.renderer.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.renderer.CallSingleVideoRenderer
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.ParticipantState
import io.getstream.video.android.mock.StreamMockUtils
import io.getstream.video.android.mock.mockCall
import io.getstream.video.android.mock.mockParticipantList

/**
 * Shows a column of call participants.
 *
 * @param call The state of the call.
 * @param participants List of participants to show.
 * @param modifier Modifier for styling.
 */
@Composable
internal fun LazyColumnVideoRenderer(
    call: Call,
    participants: List<ParticipantState>,
    primarySpeaker: ParticipantState?,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(vertical = VideoTheme.dimens.screenShareParticipantsRowPadding),
        verticalArrangement = Arrangement.spacedBy(VideoTheme.dimens.screenShareParticipantsListItemMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(items = participants, key = { it.sessionId }) { participant ->
                ListVideoRenderer(
                    call = call, participant = participant, primarySpeaker = primarySpeaker
                )
            }
        }
    )
}

/**
 * Shows a single call participant in a list.
 *
 * @param call The call state.
 * @param participant The participant to render.
 */
@Composable
private fun ListVideoRenderer(
    call: Call,
    participant: ParticipantState,
    primarySpeaker: ParticipantState?,
) {
    CallSingleVideoRenderer(
        modifier = Modifier
            .size(VideoTheme.dimens.screenShareParticipantItemSize)
            .clip(RoundedCornerShape(VideoTheme.dimens.screenShareParticipantsRadius)),
        call = call,
        participant = participant,
        labelPosition = Alignment.BottomStart,
        isScreenSharing = true,
        isFocused = participant.sessionId == primarySpeaker?.sessionId,
        isShowingConnectionQualityIndicator = false
    )
}

@Preview
@Composable
private fun ParticipantsColumnPreview() {
    StreamMockUtils.initializeStreamVideo(LocalContext.current)
    VideoTheme {
        LazyColumnVideoRenderer(
            call = mockCall, participants = mockParticipantList, primarySpeaker = mockParticipantList[0]
        )
    }
}
