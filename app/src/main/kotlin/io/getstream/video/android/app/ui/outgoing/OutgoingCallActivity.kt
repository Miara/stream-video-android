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

package io.getstream.video.android.app.ui.outgoing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.getstream.video.android.app.router.StreamRouterImpl
import io.getstream.video.android.app.videoApp
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.outcomingcall.OutgoingCallScreen

class OutgoingCallActivity : AppCompatActivity() {

    private val viewModel by viewModels<OutgoingCallViewModel> {
        OutgoingCallViewModelFactory(
            videoApp.streamVideo,
            // TODO passing [this] may lead to memory leak, cause VM may live longer than Activity
            StreamRouterImpl(this),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VideoTheme {
                OutgoingCallScreen(
                    viewModel = viewModel,
                    onCancelCall = { viewModel.hangUpCall() },
                    onMicToggleChanged = { isMicrophoneEnabled ->
                        viewModel.onMicrophoneChanged(isMicrophoneEnabled)
                    },
                    onVideoToggleChanged = { isVideoEnabled ->
                        viewModel.onVideoChanged(isVideoEnabled)
                    }
                )
            }
        }
    }

    companion object {
        internal fun getIntent(
            context: Context,
        ): Intent {
            return Intent(context, OutgoingCallActivity::class.java)
        }
    }
}
