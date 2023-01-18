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

package io.getstream.video.android.ui.xml.widget.participant

import android.content.Context
import android.util.AttributeSet
import io.getstream.video.android.ui.xml.R
import io.getstream.video.android.ui.xml.utils.extensions.use
import io.getstream.video.android.ui.xml.widget.transformer.TransformStyle

/**
 * Style for [CallParticipantsView].
 * Use this class together with [TransformStyle.callParticipantsStyleTransformer] to change [CallParticipantsView]
 * styles programmatically.
 *
 * @param callParticipantStyle The id of the custom style for [CallParticipantView] to be applied for each remote
 * participant.
 */
public data class CallParticipantsStyle(
    public val callParticipantStyle: Int,
) {

    internal companion object {
        operator fun invoke(context: Context, attrs: AttributeSet?): CallParticipantsStyle {
            context.obtainStyledAttributes(
                attrs,
                R.styleable.CallParticipantsView,
                R.attr.streamCallParticipantsViewStyle,
                R.style.Stream_CallParticipants
            ).use {
                val callParticipantStyle = it.getResourceId(
                    R.styleable.CallParticipantsView_streamCallParticipantsCallParticipantStyle,
                    context.theme.obtainStyledAttributes(
                        R.style.StreamVideoTheme,
                        intArrayOf(R.attr.streamCallParticipantViewStyle)
                    ).getResourceId(0, 0)
                )

                return CallParticipantsStyle(
                    callParticipantStyle = callParticipantStyle
                ).let(TransformStyle.callParticipantsStyleTransformer::transform)
            }
        }
    }
}
