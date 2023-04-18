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

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models

import com.squareup.moshi.Json

/**
 *
 *
 * @param id Device ID
 * @param pushProvider
 * @param pushProviderName Name of the push provider configuration
 */

data class DeviceFieldsRequest(

    /* Device ID */
    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "push_provider")
    val pushProvider: DeviceFieldsRequest.PushProvider? = null,

    /* Name of the push provider configuration */
    @Json(name = "push_provider_name")
    val pushProviderName: kotlin.String? = null

) {

    /**
     *
     *
     * Values: firebase,apn,huawei,xiaomi
     */
    enum class PushProvider(val value: kotlin.String) {
        @Json(name = "firebase")
        firebase("firebase"),
        @Json(name = "apn")
        apn("apn"),
        @Json(name = "huawei")
        huawei("huawei"),
        @Json(name = "xiaomi")
        xiaomi("xiaomi");
    }
}
