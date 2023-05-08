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

package io.getstream.video.android.compose.ui.components.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.getstream.video.android.common.util.MockUtils
import io.getstream.video.android.common.util.mockCall
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.ConnectionState
import io.getstream.video.android.core.call.state.CallAction
import io.getstream.video.android.core.call.state.ShowCallParticipantInfo
import io.getstream.video.android.core.formatAsTitle
import io.getstream.video.android.ui.common.R

@Composable
internal fun OverlayAppBar(
    call: Call,
    onBackPressed: () -> Unit = {},
    onCallAction: (CallAction) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(VideoTheme.dimens.landscapeTopAppBarHeight)
            .background(color = VideoTheme.colors.overlay),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            enabled = true,
            onClick = onBackPressed,
            modifier = Modifier.padding(
                start = VideoTheme.dimens.callAppBarLeadingContentSpacingStart,
                end = VideoTheme.dimens.callAppBarLeadingContentSpacingEnd
            )
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.stream_video_ic_arrow_back),
                contentDescription = stringResource(id = R.string.stream_video_back_button_content_description),
                tint = Color.White
            )
        }

        val connectionState by call.state.connection.collectAsState()
        val callId = when (connectionState) {
            is ConnectionState.Connected -> call.id
            else -> ""
        }
        val status = connectionState.formatAsTitle(LocalContext.current)
        val title = when (callId.isBlank()) {
            true -> status
            else -> "$status: $callId"
        }

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            color = Color.White,
            style = VideoTheme.typography.title3Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            onClick = { onCallAction(ShowCallParticipantInfo) },
            modifier = Modifier.padding(
                start = VideoTheme.dimens.callAppBarLeadingContentSpacingStart,
                end = VideoTheme.dimens.callAppBarLeadingContentSpacingEnd
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.stream_video_ic_participants),
                contentDescription = stringResource(id = R.string.stream_video_call_participants_menu_content_description),
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun OverlayAppBarPreview() {
    MockUtils.initializeStreamVideo(LocalContext.current)
    VideoTheme {
        OverlayAppBar(
            call = mockCall,
            onBackPressed = {}
        ) {}
    }
}
