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
 * This event is sent to call participants to notify when a user is blocked on a call, clients can use this event to show a notification.  If the user is the current user, the client should leave the call screen as well
 *
 * @param callCid 
 * @param createdAt 
 * @param type The type of event: \"call.blocked_user\" in this case
 * @param user 
 * @param blockedByUser 
 */


data class BlockedUserEvent (

    @Json(name = "call_cid")
    val callCid: kotlin.String,

    @Json(name = "created_at")
    val createdAt: org.threeten.bp.OffsetDateTime,

    /* The type of event: \"call.blocked_user\" in this case */
    @Json(name = "type")
    val type: kotlin.String = "call.blocked_user",

    @Json(name = "user")
    val user: UserResponse,

    @Json(name = "blocked_by_user")
    val blockedByUser: UserResponse? = null

) : VideoEvent(), WSCallEvent{ 

    override fun getCallCID(): String {
        return callCid
    }

    override fun getEventType(): String {
        return type
    }
}



