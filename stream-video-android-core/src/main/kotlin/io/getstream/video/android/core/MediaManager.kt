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

package io.getstream.video.android.core

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import androidx.core.content.getSystemService
import io.getstream.log.taggedLogger
import io.getstream.video.android.core.audio.AudioHandler
import io.getstream.video.android.core.audio.AudioSwitchHandler
import io.getstream.video.android.core.model.CallSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.webrtc.Camera2Capturer
import org.webrtc.Camera2Enumerator
import org.webrtc.CameraEnumerationAndroid
import org.webrtc.CameraEnumerator
import org.webrtc.CapturerObserver
import org.webrtc.EglBase
import org.webrtc.SurfaceTextureHelper
import org.webrtc.VideoCapturer
import org.webrtc.VideoFrame
import org.webrtc.VideoSource

sealed class DeviceStatus {
    object Disabled : DeviceStatus()
    object Enabled : DeviceStatus()
    object NoPermission : DeviceStatus()
}

class SpeakerManager(val mediaManager: MediaManagerImpl) {

    val _status = MutableStateFlow<DeviceStatus>(DeviceStatus.Disabled)
    val status: StateFlow<DeviceStatus> = _status

    val _selectedDevice = MutableStateFlow<String?>(null)
    val selectedDevice: StateFlow<String?> = _selectedDevice

    val _devices = MutableStateFlow<List<String>>(emptyList())
    val devices: StateFlow<List<String>> = _devices

    val _speakerPhoneEnabled = MutableStateFlow(false)
    val speakerPhoneEnabled: StateFlow<Boolean> = _speakerPhoneEnabled

    fun devices(): List<String> {
        return mediaManager.getCameraDevices()
    }

    fun select(deviceId: String) {
        mediaManager.selectCamera(deviceId)
    }

    fun enable() {
        mediaManager.setSpeakerphoneEnabled(true)
    }

    fun setVolume(volumeLevel: Long) {
    }

    fun setSpeakerPhone(speakerPhone: Boolean) {
        mediaManager.setSpeakerphoneEnabled(speakerPhone)
        _speakerPhoneEnabled.value = speakerPhone
    }

    fun setEnabled(enabled: Boolean) {
        mediaManager.setCameraEnabled(enabled)
    }

    fun disable() {
        mediaManager.setCameraEnabled(false)
    }
}

class MicrophoneManager(val mediaManager: MediaManagerImpl) {

    val _status = MutableStateFlow<DeviceStatus>(DeviceStatus.Disabled)
    val status: StateFlow<DeviceStatus> = _status

    val _selectedDevice = MutableStateFlow<String?>(null)
    val selectedDevice: StateFlow<String?> = _selectedDevice

    val _devices = MutableStateFlow<List<String>>(emptyList())
    val devices: StateFlow<List<String>> = _devices

    fun devices(): List<String> {
        return mediaManager.getCameraDevices()
    }

    fun select(deviceId: String) {
        mediaManager.selectCamera(deviceId)
    }
    fun setEnabled(enabled: Boolean) {
        mediaManager.setCameraEnabled(enabled)
    }
    fun startCapture() {
    }

    fun enable() {
        mediaManager.setCameraEnabled(true)
    }

    fun disable() {
        mediaManager.setCameraEnabled(false)
    }
}

public class CameraManager(public val mediaManager: MediaManagerImpl) {

    private val _status = MutableStateFlow<DeviceStatus>(DeviceStatus.Disabled)
    public val status: StateFlow<DeviceStatus> = _status

    private val _selectedDevice = MutableStateFlow<String?>(null)
    public val selectedDevice: StateFlow<String?> = _selectedDevice

    fun flip() {
        mediaManager.flipCamera()
    }

    private val _devices = MutableStateFlow<List<String>>(emptyList())
    public val devices: StateFlow<List<String>> = _devices

//    fun devices(): List<String> {
//        return mediaManager.getCameraDevices()
//    }

    fun select(deviceId: String) {
        mediaManager.selectCamera(deviceId)
    }

    fun startCapture() {

        mediaManager.startCapturingLocalVideo(CameraMetadata.LENS_FACING_FRONT)

    }

    fun enable() {
        mediaManager.setCameraEnabled(true)
    }

    fun setEnabled(enabled: Boolean) {
        mediaManager.setCameraEnabled(enabled)
    }

    fun disable() {
        mediaManager.setCameraEnabled(false)
    }
}

/**
 * Wrap all the audio/video interactions
 * This makes it easier to test our codebase
 *
 * This class knows about audio/ video.
 * It shouldn't be aware of webrtc tracks. Those are handled in the RtcSession
 *
 * @see RtcSession
 *
 * The Rtc session observes these stateflows and updates accordingly
 *
 * Also see:
 *
 * @see AudioSwitchHandler
 * @see AudioSwitch
 * @see BluetoothHeadsetManager
 */
class MediaManagerImpl(val context: Context, val scope: CoroutineScope, eglBaseContext: EglBase.Context) : CapturerObserver {

    private var audioManager = context.getSystemService<AudioManager>()
    internal var captureResolution: CameraEnumerationAndroid.CaptureFormat? = null
    private var isCapturingVideo: Boolean = false
    var videoCapturer: Camera2Capturer? = null
    private val logger by taggedLogger("Call:MediaManagerImpl")
    private val cameraManager = context.getSystemService<CameraManager>()
    private val cameraEnumerator: CameraEnumerator by lazy {
        Camera2Enumerator(context)
    }

    val camera = CameraManager(this)

    // TODO: maybe merge microphone and speaker, not sure many apps allow you to split the concepts
    val microphone = MicrophoneManager(this)
    val speaker = SpeakerManager(this)

    val enumerator = Camera2Enumerator(context)

    fun toggle() {
        TODO()
    }

    fun setSpeakerphoneEnabled(isEnabled: Boolean) {
        val devices = getAudioDevices()

        val activeDevice = devices.firstOrNull {
            if (isEnabled) {
                it.name.contains("speaker", true)
            } else {
                !it.name.contains("speaker", true)
            }
        }

        getAudioHandler()?.selectDevice(activeDevice)
    }

    fun selectAudioDevice(device: io.getstream.video.android.core.audio.AudioDevice) {
        logger.d { "[selectAudioDevice] #sfu; device: $device" }
        val handler = getAudioHandler() ?: return

        handler.selectDevice(device)
    }

    fun getCameraDevices(): List<String> {

        val ids = cameraManager!!.cameraIdList
        val names = enumerator.deviceNames.toList()
        val manager = cameraManager

        for (id in ids) {
            val characteristics = manager.getCameraCharacteristics(id)
            val cameraLensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)

            val supportedFormats = enumerator.getSupportedFormats(names.first())
        }
        return names
    }

    public fun getAudioHandler(): AudioSwitchHandler? {
        return audioHandler as? AudioSwitchHandler
    }

    private val surfaceTextureHelper by lazy {
        SurfaceTextureHelper.create(
            "CaptureThread", eglBaseContext
        )
    }

    fun startCapturingLocalVideo(position: Int) {
        val mediaManager = this
        videoCapturer = buildCameraCapturer() as Camera2Capturer?
        runBlocking(scope.coroutineContext) {
            videoCapturer?.initialize(
                surfaceTextureHelper,
                context,
                mediaManager
            )
        }
        val enumerator = cameraEnumerator as? Camera2Enumerator ?: return

        val frontCamera = enumerator.deviceNames.first {
            if (position == 0) {
                enumerator.isFrontFacing(it)
            } else {
                enumerator.isBackFacing(it)
            }
        }

        val supportedFormats = enumerator.getSupportedFormats(frontCamera) ?: emptyList()

        // TODO: server uses 960 480 240
        val resolution = supportedFormats.firstOrNull {
            (it.width == 720 || it.width == 480 || it.width == 360)
        } ?: throw java.lang.IllegalStateException("No supported resolution found")
        runBlocking(scope.coroutineContext) {
            videoCapturer!!.startCapture(resolution.width, resolution.height, 30)
        }
        isCapturingVideo = true
        captureResolution = resolution
    }


    fun buildCameraCapturer(): VideoCapturer? {
        val manager = cameraManager ?: return null

        val ids = manager.cameraIdList
        var foundCamera = false
        var cameraId = ""

        for (id in ids) {
            val characteristics = manager.getCameraCharacteristics(id)
            val cameraLensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)

            if (cameraLensFacing == CameraMetadata.LENS_FACING_FRONT) {
                foundCamera = true
                cameraId = id
            }
        }

        if (!foundCamera && ids.isNotEmpty()) {
            cameraId = ids.first()
        }

        val camera2Capturer = Camera2Capturer(context, cameraId, null)
        videoCapturer = camera2Capturer
        return camera2Capturer
    }

    fun getAudioDevices(): List<io.getstream.video.android.core.audio.AudioDevice> {
        logger.d { "[getAudioDevices] #sfu; no args" }
        val handler = getAudioHandler() ?: return emptyList()

        return handler.availableAudioDevices
    }

    internal val audioHandler: AudioHandler by lazy {
        AudioSwitchHandler(context)
    }

    internal fun setupAudio(callSettings: CallSettings) {
        logger.d { "[setupAudio] #sfu; no args" }
        audioHandler.start()
        audioManager?.mode = AudioManager.MODE_IN_COMMUNICATION

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val devices = audioManager?.availableCommunicationDevices ?: return
            val deviceType = if (callSettings.speakerOn) {
                AudioDeviceInfo.TYPE_BUILTIN_SPEAKER
            } else {
                AudioDeviceInfo.TYPE_BUILTIN_EARPIECE
            }

            val device = devices.firstOrNull { it.type == deviceType } ?: return

            val isCommunicationDeviceSet = audioManager?.setCommunicationDevice(device)
            logger.d { "[setupAudio] #sfu; isCommunicationDeviceSet: $isCommunicationDeviceSet" }
        }
    }

    fun flipCamera() {
        videoCapturer?.switchCamera(null)
    }

    fun setCameraEnabled(b: Boolean) {
        TODO()
        // activeSession!!.setCameraEnabled(false)
    }

    fun selectCamera(cameraId: String) {
        val camera2Capturer = Camera2Capturer(context, cameraId, null)
        val capturer = camera2Capturer as? Camera2Capturer

        val supportedFormats = enumerator.getSupportedFormats(cameraId) ?: emptyList()

        val resolution = supportedFormats.firstOrNull {
            (it.width == 720 || it.width == 480 || it.width == 360)
        }

        capturer!!.startCapture(resolution!!.width, resolution.height, 30)
    }

    override fun onCapturerStarted(success: Boolean) {
        //
    }

    override fun onCapturerStopped() {
        //
    }

    override fun onFrameCaptured(frame: VideoFrame?) {
        //
    }

    fun setVideoSource(videoSource: VideoSource) {
        runBlocking(scope.coroutineContext) {
            videoCapturer?.initialize(
                surfaceTextureHelper,
                context,
                videoSource.capturerObserver
            )
        }
    }
}
