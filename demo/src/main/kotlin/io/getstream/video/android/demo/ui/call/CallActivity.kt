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

package io.getstream.video.android.demo.ui.call

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.CallContainer
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.call.state.ToggleCamera
import io.getstream.video.android.core.call.state.ToggleMicrophone
import io.getstream.video.android.core.model.CallType
import io.getstream.video.android.core.model.StreamCallId
import io.getstream.video.android.core.model.mapper.toTypeAndId
import io.getstream.video.android.core.permission.PermissionManager
import io.getstream.video.android.core.viewmodel.CallViewModel
import io.getstream.video.android.core.viewmodel.CallViewModelFactory
import io.getstream.video.android.demo.demoVideoApp

class CallActivity : AppCompatActivity() {

    /**
     * Provides the StreamVideo instance through the videoApp.
     */
    private val streamVideo: StreamVideo by lazy { demoVideoApp.streamVideo }

    private val factory by lazy { callViewModelFactory() }
    private val vm by viewModels<CallViewModel> { factory }

    // private val permissionsManager = setupPermissionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.joinCall {
            Toast.makeText(
                this,
                "failed to join call (${vm.call.cid}): $it", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        vm.setOnLeaveCall { finish() }

        setContent {
            VideoTheme {
                CallContainer(
                    modifier = Modifier.background(color = VideoTheme.colors.appBackground),
                    callViewModel = vm,
                    callType = CallType.VIDEO,
                    onBackPressed = { finish() },
                )
            }
        }
    }

    /**
     * Provides the default ViewModel factory.
     */
    private fun callViewModelFactory(): CallViewModelFactory {
        val (type, id) = intent.getStringExtra(EXTRA_CID)?.toTypeAndId()
            ?: throw IllegalArgumentException("You must pass correct channel id.")

        return CallViewModelFactory(
            streamVideo = streamVideo,
            call = streamVideo.call(type = type, id = id),
            permissionManager = initPermissionManager()
        )
    }

    private fun initPermissionManager(): PermissionManager {
        return PermissionManager.create(
            activity = this,
            onPermissionResult = { permission, isGranted ->
                when (permission) {
                    android.Manifest.permission.CAMERA -> vm.onCallAction(ToggleCamera(isGranted))
                    android.Manifest.permission.RECORD_AUDIO -> vm.onCallAction(
                        ToggleMicrophone(
                            isGranted
                        )
                    )
                }
            },
            onShowRequestPermissionRationale = {}
        )
    }

    companion object {
        internal const val EXTRA_CID = "EXTRA_CID"

        fun getIntent(context: Context, cid: StreamCallId): Intent {
            return Intent(context, CallActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(EXTRA_CID, cid)
            }
        }
    }

//    fun setupPermissionManager(): StreamPermissionManagerImpl {
//        return StreamPermissionManagerImpl(
//            fragmentActivity = this,
//            onPermissionResult = { permission, isGranted ->
//                when (permission) {
// //                    Manifest.permission.CAMERA -> callViewModel.onCallAction(ToggleCamera(isGranted))
// //                    Manifest.permission.RECORD_AUDIO -> callViewModel.onCallAction(
// //                        ToggleMicrophone(
// //                            isGranted
// //                        )
// //                    )
//                }
//            },
//            onShowSettings = {
//                //
//            })
//    }
    /**
     * Provides a custom factory for the ViewModel, that provides fake users for invites.
     */
}
