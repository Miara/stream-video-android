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

import org.openapitools.client.models.TargetResolution




import com.squareup.moshi.Json

/**
 * 
 *
 * @param accessRequestEnabled 
 * @param cameraDefaultOn 
 * @param cameraFacing 
 * @param enabled 
 * @param targetResolution 
 */


data class VideoSettings (

    @Json(name = "access_request_enabled")
    val accessRequestEnabled: kotlin.Boolean,

    @Json(name = "camera_default_on")
    val cameraDefaultOn: kotlin.Boolean,

    @Json(name = "camera_facing")
    val cameraFacing: VideoSettings.CameraFacing,

    @Json(name = "enabled")
    val enabled: kotlin.Boolean,

    @Json(name = "target_resolution")
    val targetResolution: TargetResolution

)

{

    /**
     * 
     *
     * Values: front,back,`external`
     */
    enum class CameraFacing(val value: kotlin.String) {
        @Json(name = "front") front("front"),
        @Json(name = "back") back("back"),
        @Json(name = "external") `external`("external");
    }

}



