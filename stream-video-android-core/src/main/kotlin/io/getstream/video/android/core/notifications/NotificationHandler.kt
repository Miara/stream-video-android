package io.getstream.video.android.core.notifications

import io.getstream.video.android.model.StreamCallId

public interface NotificationHandler {
    fun onRiningCall(callId: StreamCallId, callDisplayName: String)
    fun onNotification(callId: StreamCallId)
    fun onLivestream(callId: StreamCallId)

    companion object {
        const val ACTION_INCOMING_CALL = "io.getstream.video.android.action.INCOMING_CALL"
        const val ACTION_ACCEPT_CALL = "io.getstream.video.android.action.ACCEPT_CALL"
        const val ACTION_REJECT_CALL = "io.getstream.video.android.action.REJECT_CALL"
        const val INCOMING_CALL_NOTIFICATION_ID = 24756
    }
}