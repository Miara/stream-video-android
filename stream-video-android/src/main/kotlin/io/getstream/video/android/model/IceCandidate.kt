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

package io.getstream.video.android.model

@kotlinx.serialization.Serializable()
public class IceCandidate(
    internal val sdpMid: String,
    internal val sdpMLineIndex: Int,
    internal val candidate: String,
    internal val usernameFragment: String?
)

internal fun org.webrtc.IceCandidate.toCandidate(): IceCandidate {
    return IceCandidate(
        sdpMid, sdpMLineIndex, sdp, ""
    )
}

internal fun IceCandidate.toCandidate(): org.webrtc.IceCandidate {
    return org.webrtc.IceCandidate(
        sdpMid, sdpMLineIndex, candidate
    )
}
