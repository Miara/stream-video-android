/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models

import org.openapitools.client.models.CallResponse
import org.openapitools.client.models.DatacenterResponse
import org.openapitools.client.models.MemberResponse

import com.squareup.moshi.Json

/**
 * 
 *
 * @param call 
 * @param created 
 * @param duration 
 * @param edges 
 * @param members 
 * @param membership 
 */


internal data class JoinCallResponse (

    @Json(name = "call")
    val call: CallResponse,

    @Json(name = "created")
    val created: kotlin.Boolean? = null,

    @Json(name = "duration")
    val duration: kotlin.String? = null,

    @Json(name = "edges")
    val edges: kotlin.collections.List<DatacenterResponse>? = null,

    @Json(name = "members")
    val members: kotlin.collections.List<MemberResponse>? = null,

    @Json(name = "membership")
    val membership: MemberResponse? = null

)

