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

package io.getstream.video.android.xml.binding

import androidx.lifecycle.LifecycleOwner
import io.getstream.video.android.core.viewmodel.CallViewModel
import io.getstream.video.android.xml.widget.call.CallView
import io.getstream.video.android.xml.widget.participant.internal.CallParticipantsListView
import io.getstream.video.android.xml.widget.screenshare.ScreenShareView
import kotlinx.coroutines.flow.combine

public fun CallView.bindView(
    viewModel: CallViewModel,
    lifecycleOwner: LifecycleOwner
) {
    startJob(lifecycleOwner) {
        viewModel.participantList.combine(viewModel.screenSharingSessions) { participants, screenSharingSessions ->
            participants to screenSharingSessions.firstOrNull()
        }.collect { (participants, screenSharingSession) ->
            if (screenSharingSession != null) {
                setScreenSharingContent { view ->
                    when (view) {
                        is ScreenShareView -> view.bindView(viewModel, lifecycleOwner)
                        is CallParticipantsListView -> view.bindView(viewModel, lifecycleOwner)
                    }
                }
                updatePresenterText(screenSharingSession.participant.name.ifEmpty { screenSharingSession.participant.id })
                setFloatingParticipant(null)
            } else {
                setNormalContent { it.bindView(viewModel, lifecycleOwner) }
                val localParticipant = if (participants.size == 1 || participants.size == 4) {
                    null
                } else {
                    participants.firstOrNull { it.isLocal }
                }
                setFloatingParticipant(localParticipant) { it.bindView(viewModel, lifecycleOwner) }
            }
        }
    }
}
