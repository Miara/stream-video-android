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

package io.getstream.video.android.compose.ui.components.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.background.ParticipantImageBackground
import io.getstream.video.android.core.model.User

@Composable
public fun UserAvatarBackground(
    user: User,
    modifier: Modifier = Modifier,
    shape: Shape = VideoTheme.shapes.avatar,
    avatarSize: Dp = 72.dp,
    textStyle: TextStyle = VideoTheme.typography.title3Bold,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    requestSize: IntSize = IntSize(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE),
    initialsAvatarOffset: DpOffset = DpOffset(0.dp, 0.dp),
    @DrawableRes previewPlaceholder: Int = LocalAvatarPreviewProvider.getLocalAvatarPreviewPlaceholder(),
    @DrawableRes loadingPlaceholder: Int? = LocalAvatarPreviewProvider.getLocalAvatarLoadingPlaceholder(),
) {
    Box(modifier = modifier) {
        ParticipantImageBackground(
            modifier = Modifier.fillMaxSize(),
            userImage = user.image
        )

        UserAvatar(
            modifier = Modifier
                .size(avatarSize)
                .align(Alignment.Center),
            user = user,
            shape = shape,
            textStyle = textStyle,
            contentScale = contentScale,
            contentDescription = contentDescription,
            requestSize = requestSize,
            initialsAvatarOffset = initialsAvatarOffset,
            previewPlaceholder = previewPlaceholder,
            loadingPlaceholder = loadingPlaceholder
        )
    }
}
