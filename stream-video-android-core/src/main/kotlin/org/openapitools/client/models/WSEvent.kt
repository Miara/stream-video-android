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

import org.openapitools.client.models.BlockedUserEvent
import org.openapitools.client.models.CallAcceptedEvent
import org.openapitools.client.models.CallCreatedEvent
import org.openapitools.client.models.CallEndedEvent
import org.openapitools.client.models.CallReactionEvent
import org.openapitools.client.models.CallRecordingStartedEvent
import org.openapitools.client.models.CallRecordingStoppedEvent
import org.openapitools.client.models.CallRejectedEvent
import org.openapitools.client.models.CallResponse
import org.openapitools.client.models.CallUpdatedEvent
import org.openapitools.client.models.CustomVideoEvent
import org.openapitools.client.models.HealthCheckEvent
import org.openapitools.client.models.MemberResponse
import org.openapitools.client.models.OwnCapability
import org.openapitools.client.models.OwnUserResponse
import org.openapitools.client.models.PermissionRequestEvent
import org.openapitools.client.models.ReactionResponse
import org.openapitools.client.models.UnblockedUserEvent
import org.openapitools.client.models.UpdatedCallPermissionsEvent
import org.openapitools.client.models.UserResponse
import org.openapitools.client.models.WSConnectedEvent

import com.squareup.moshi.Json

/**
 * The discriminator object for all websocket events, you should use this to map event payloads to their own type
 *
 * @param callCid 
 * @param createdAt 
 * @param type The type of event: \"connection.ok\" in this case
 * @param user 
 * @param call 
 * @param members the members added to this call
 * @param ringing true when the call was created with ring enabled
 * @param reaction 
 * @param capabilitiesByRole The capabilities by role for this call
 * @param custom Custom data for this object
 * @param connectionId The connection_id for this client
 * @param permissions The list of permissions requested by the user
 * @param ownCapabilities The capabilities of the current user
 * @param me 
 * @param blockedByUser 
 */


interface WSEvent {

    @Json(name = "call_cid")
    val callCid: kotlin.String
    @Json(name = "created_at")
    val createdAt: java.time.OffsetDateTime
    /* The type of event: \"connection.ok\" in this case */
    @Json(name = "type")
    val type: kotlin.String
    @Json(name = "user")
    val user: UserResponse
    @Json(name = "call")
    val call: CallResponse
    /* the members added to this call */
    @Json(name = "members")
    val members: kotlin.collections.List<MemberResponse>
    /* true when the call was created with ring enabled */
    @Json(name = "ringing")
    val ringing: kotlin.Boolean
    @Json(name = "reaction")
    val reaction: ReactionResponse
    /* The capabilities by role for this call */
    @Json(name = "capabilities_by_role")
    val capabilitiesByRole: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>
    /* Custom data for this object */
    @Json(name = "custom")
    val custom: kotlin.collections.Map<kotlin.String, kotlin.Any>
    /* The connection_id for this client */
    @Json(name = "connection_id")
    val connectionId: kotlin.String
    /* The list of permissions requested by the user */
    @Json(name = "permissions")
    val permissions: kotlin.collections.List<kotlin.String>
    /* The capabilities of the current user */
    @Json(name = "own_capabilities")
    val ownCapabilities: kotlin.collections.List<OwnCapability>
    @Json(name = "me")
    val me: OwnUserResponse
    @Json(name = "blocked_by_user")
    val blockedByUser: UserResponse?
}

