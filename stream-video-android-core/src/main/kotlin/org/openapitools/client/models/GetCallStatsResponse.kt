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

import org.openapitools.client.models.CallTimeline
import org.openapitools.client.models.SFULocationResponse
import org.openapitools.client.models.Stats
import org.openapitools.client.models.UserStats




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
 * @param callDurationSeconds
 * @param callStatus
 * @param duration Duration of the request in human-readable format
 * @param maxFreezesDurationSeconds
 * @param maxParticipants
 * @param maxTotalQualityLimitationDurationSeconds
 * @param participantReport
 * @param publishingParticipants
 * @param qualityScore
 * @param sfuCount
 * @param sfus
 * @param callTimeline
 * @param jitter
 * @param latency
 */


data class GetCallStatsResponse (

    @Json(name = "call_duration_seconds")
    val callDurationSeconds: kotlin.Int,

    @Json(name = "call_status")
    val callStatus: kotlin.String,

    /* Duration of the request in human-readable format */
    @Json(name = "duration")
    val duration: kotlin.String,

    @Json(name = "max_freezes_duration_seconds")
    val maxFreezesDurationSeconds: kotlin.Int,

    @Json(name = "max_participants")
    val maxParticipants: kotlin.Int,

    @Json(name = "max_total_quality_limitation_duration_seconds")
    val maxTotalQualityLimitationDurationSeconds: kotlin.Int,

    @Json(name = "participant_report")
    val participantReport: kotlin.collections.List<UserStats>,

    @Json(name = "publishing_participants")
    val publishingParticipants: kotlin.Int,

    @Json(name = "quality_score")
    val qualityScore: kotlin.Int,

    @Json(name = "sfu_count")
    val sfuCount: kotlin.Int,

    @Json(name = "sfus")
    val sfus: kotlin.collections.List<SFULocationResponse>,

    @Json(name = "call_timeline")
    val callTimeline: CallTimeline? = null,

    @Json(name = "jitter")
    val jitter: Stats? = null,

    @Json(name = "latency")
    val latency: Stats? = null

)
