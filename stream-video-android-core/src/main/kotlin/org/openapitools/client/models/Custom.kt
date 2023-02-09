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

import org.openapitools.client.models.UserResponse

import com.squareup.moshi.Json

/**
 * 
 *
 * @param type 
 * @param callCid Call CID
 * @param createdAt 
 * @param custom 
 * @param user 
 */


internal data class Custom (

    @Json(name = "type")
    val type: kotlin.String,

    /* Call CID */
    @Json(name = "call_cid")
    val callCid: kotlin.String? = null,

    @Json(name = "created_at")
    val createdAt: java.time.OffsetDateTime? = null,

    @Json(name = "custom")
    val custom: kotlin.collections.Map<kotlin.String, kotlin.Any>? = null,

    @Json(name = "user")
    val user: UserResponse? = null

)

