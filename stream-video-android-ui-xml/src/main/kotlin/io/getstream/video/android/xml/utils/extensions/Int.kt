/*
 * Copyright (c) 2014-2024 Stream.io Inc. All rights reserved.
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

package io.getstream.video.android.xml.utils.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

/**
 * Transforms DP value integer to pixels, based on the screen density.
 */
@JvmSynthetic
internal fun Int.dpToPx(): Int = dpToPxPrecise().roundToInt()

/**
 * Uses the display metrics to transform the value of DP to pixels.
 */
@JvmSynthetic
internal fun Int.dpToPxPrecise(): Float = (this * displayMetrics().density)

/**
 * Fetches the current system display metrics based on [Resources].
 */
@JvmSynthetic
internal fun displayMetrics(): DisplayMetrics = Resources.getSystem().displayMetrics
