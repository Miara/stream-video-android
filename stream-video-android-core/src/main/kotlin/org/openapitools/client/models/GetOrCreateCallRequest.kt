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

import org.openapitools.client.models.CallRequest
import org.openapitools.client.models.PaginationParamsRequest

import com.squareup.moshi.Json

/**
 * 
 *
 * @param `data` 
 * @param members 
 * @param ring 
 */


internal data class GetOrCreateCallRequest (

    @Json(name = "data")
    val `data`: CallRequest? = null,

    @Json(name = "members")
    val members: PaginationParamsRequest? = null,

    @Json(name = "ring")
    val ring: kotlin.Boolean? = null

)

