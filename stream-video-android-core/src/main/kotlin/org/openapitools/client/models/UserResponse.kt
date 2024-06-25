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

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models





import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import org.openapitools.client.infrastructure.Serializer

/**
 *
 *
 * @param banned
 * @param createdAt Date/time of creation
 * @param custom
 * @param id
 * @param language
 * @param online
 * @param role
 * @param teams
 * @param updatedAt Date/time of the last update
 * @param deactivatedAt
 * @param deletedAt Date/time of deletion
 * @param image
 * @param lastActive
 * @param name
 * @param revokeTokensIssuedBefore
 */


data class UserResponse (

    @Json(name = "banned")
    val banned: kotlin.Boolean = false,

    /* Date/time of creation */
    @Json(name = "created_at")
    val createdAt: org.threeten.bp.OffsetDateTime,

    @Json(name = "custom")
    val custom: kotlin.collections.Map<kotlin.String, kotlin.Any?>,

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "language")
    val language: kotlin.String,

    @Json(name = "online")
    val online: kotlin.Boolean,

    @Json(name = "role")
    val role: kotlin.String,

    @Json(name = "teams")
    val teams: kotlin.collections.List<kotlin.String>,

    /* Date/time of the last update */
    @Json(name = "updated_at")
    val updatedAt: org.threeten.bp.OffsetDateTime,

    @Json(name = "deactivated_at")
    val deactivatedAt: org.threeten.bp.OffsetDateTime? = null,

    /* Date/time of deletion */
    @Json(name = "deleted_at")
    val deletedAt: org.threeten.bp.OffsetDateTime? = null,

    @Json(name = "image")
    val image: kotlin.String? = null,

    @Json(name = "last_active")
    val lastActive: org.threeten.bp.OffsetDateTime? = null,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "revoke_tokens_issued_before")
    val revokeTokensIssuedBefore: org.threeten.bp.OffsetDateTime? = null

)
