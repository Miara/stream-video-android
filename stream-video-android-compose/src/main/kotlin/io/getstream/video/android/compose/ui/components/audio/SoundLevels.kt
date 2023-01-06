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

package io.getstream.video.android.compose.ui.components.audio

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.theme.VideoTheme

/**
 * Used to indicate the active sound levels of a given participant.
 */
@Composable
public fun ActiveSoundLevels(modifier: Modifier) {
    val color = VideoTheme.colors.primaryAccent

    val firstLevel = remember { Animatable(0.5f) }
    val secondLevel = remember { Animatable(1f) }
    val thirdLevel = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        firstLevel.animateTo(
            1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 800

                    0.5f at 0
                    0.75f at 200
                    1f at 400
                    0.75f at 600
                    0.5f at 800
                }
            )
        )
    }

    LaunchedEffect(Unit) {
        secondLevel.animateTo(
            0.5f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 800

                    1f at 0
                    0.75f at 200
                    0.5f at 400
                    0.75f at 600
                    1f at 800
                }
            )
        )
    }

    LaunchedEffect(Unit) {
        thirdLevel.animateTo(
            1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 800

                    0.5f at 0
                    0.75f at 200
                    1f at 400
                    0.75f at 600
                    0.5f at 800
                }
            )
        )
    }

    Row(
        modifier = modifier.size(height = 16.dp, width = 20.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight(firstLevel.value)
                .background(color = color, shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(3.dp))

        Spacer(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight(fraction = secondLevel.value)
                .background(color = color, shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(3.dp))

        Spacer(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight(fraction = thirdLevel.value)
                .background(color = color, shape = RoundedCornerShape(16.dp))
        )
    }
}
