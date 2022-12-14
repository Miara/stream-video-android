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

package io.getstream.video.android.compose.ui.components.participants

import android.view.View
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.participants.internal.Participants
import io.getstream.video.android.model.Call

/**
 * Renders all the CallParticipants, based on the number of people in a call and the call state.
 *
 * @param call The call that contains all the participants state and tracks.
 * @param modifier Modifier for styling.
 * @param onRender Handler when each of the Video views render their first frame.
 */
@Composable
public fun CallParticipants(
    call: Call,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onRender: (View) -> Unit = {}
) {
    var bounds by remember { mutableStateOf(Rect(0f, 0f, 0f, 0f)) }

    BoxWithConstraints(
        modifier = modifier
            .padding(paddingValues)
            .onGloballyPositioned {
                bounds = it.boundsInParent()
            }
    ) {
        Participants(
            modifier = Modifier.fillMaxSize(),
            call = call,
            onRender = onRender
        )

        val localParticipantState by call.localParticipant.collectAsState(initial = null)
        val currentLocal = localParticipantState

        if (currentLocal != null) {
            FloatingParticipantItem(
                call = call,
                localParticipant = currentLocal,
                parentBounds = bounds,
                modifier = Modifier
                    .size(
                        height = VideoTheme.dimens.floatingVideoHeight,
                        width = VideoTheme.dimens.floatingVideoWidth
                    )
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}
