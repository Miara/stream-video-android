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

package io.getstream.video.android.core.notifications

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.CallStyle
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.content.ContextCompat
import io.getstream.android.push.permissions.DefaultNotificationPermissionHandler
import io.getstream.android.push.permissions.NotificationPermissionHandler
import io.getstream.log.TaggedLogger
import io.getstream.log.taggedLogger
import io.getstream.video.android.core.R
import io.getstream.video.android.core.RingingState
import io.getstream.video.android.core.notifications.NotificationHandler.Companion.ACTION_LIVE_CALL
import io.getstream.video.android.core.notifications.NotificationHandler.Companion.ACTION_NOTIFICATION
import io.getstream.video.android.core.notifications.NotificationHandler.Companion.INCOMING_CALL_NOTIFICATION_ID
import io.getstream.video.android.core.notifications.internal.DefaultStreamIntentResolver
import io.getstream.video.android.core.notifications.internal.service.CallService
import io.getstream.video.android.model.StreamCallId

public open class DefaultNotificationHandler(
    private val application: Application,
    private val notificationPermissionHandler: NotificationPermissionHandler =
        DefaultNotificationPermissionHandler
            .createDefaultNotificationPermissionHandler(application),
    /**
     * Set this to true if you want to make the ringing notifications as low-priority
     * in case the application is in foreground. This will prevent the notification from
     * interrupting the user while he is in the app. In this case you need to make sure to
     * handle this call state and display an incoming call screen.
     */
    val hideRingingNotificationInForeground: Boolean = false,

    /**
     * The notification icon for call notifications.
     */
    @DrawableRes val notificationIconRes: Int = android.R.drawable.ic_menu_call,
) : NotificationHandler,
    NotificationPermissionHandler by notificationPermissionHandler {

    private val logger: TaggedLogger by taggedLogger("Video:DefaultNotificationHandler")
    private val intentResolver = DefaultStreamIntentResolver(application)
    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(application).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.createNotificationChannel(
                    NotificationChannelCompat
                        .Builder(
                            getChannelId(),
                            NotificationManager.IMPORTANCE_HIGH,
                        )
                        .setName(getChannelName())
                        .setDescription(getChannelDescription())
                        .build(),
                )
            }
        }
    }

    override fun onRingingCall(callId: StreamCallId, callDisplayName: String) {
        val serviceIntent = CallService.buildStartIntent(
            this.application,
            callId,
            CallService.TRIGGER_INCOMING_CALL,
            callDisplayName,
        )
        ContextCompat.startForegroundService(application.applicationContext, serviceIntent)
    }

    override fun getRingingCallNotification(
        ringingState: RingingState,
        callId: StreamCallId,
        callDisplayName: String,
    ): Notification? {
        return if (ringingState is RingingState.Incoming) {
            val fullScreenPendingIntent = intentResolver.searchIncomingCallPendingIntent(callId)
            val acceptCallPendingIntent = intentResolver.searchAcceptCallPendingIntent(callId)
            val rejectCallPendingIntent = intentResolver.searchRejectCallPendingIntent(callId)

            if (fullScreenPendingIntent != null && acceptCallPendingIntent != null && rejectCallPendingIntent != null) {
                getIncomingCallNotification(
                    fullScreenPendingIntent,
                    acceptCallPendingIntent,
                    rejectCallPendingIntent,
                    callDisplayName,
                )
            } else {
                logger.e { "Ringing call notification not shown, one of the intents is null." }
                null
            }
        } else if (ringingState is RingingState.Outgoing) {
            val outgoingCallPendingIntent = intentResolver.searchOutgoingCallPendingIntent(callId)
            val endCallPendingIntent = intentResolver.searchEndCallPendingIntent(callId)

            if (outgoingCallPendingIntent != null && endCallPendingIntent != null) {
                getOutgoingCallNotification(
                    outgoingCallPendingIntent,
                    endCallPendingIntent,
                    callDisplayName,
                )
            } else {
                logger.e { "Ringing call notification not shown, one of the intents is null." }
                null
            }
        } else {
            null
        }
    }

    private fun getIncomingCallNotification(
        fullScreenPendingIntent: PendingIntent,
        acceptCallPendingIntent: PendingIntent,
        rejectCallPendingIntent: PendingIntent,
        callDisplayName: String,
    ): Notification {
        // if the app is in foreground then don't interrupt the user with a high priority
        // notification (popup). The application will display an incoming ringing call
        // screen instead - but this needs to be handled by the application.
        // The default behaviour is that all notification are high priority
        val showAsHighPriority = !hideRingingNotificationInForeground || !isInForeground()

        val channelId = application.getString(
            if (showAsHighPriority) {
                R.string.stream_video_incoming_call_notification_channel_id
            } else {
                R.string.stream_video_incoming_call_low_priority_notification_channel_id
            },
        )
        maybeCreateChannel(channelId, application) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                description = application.getString(
                    if (showAsHighPriority) {
                        R.string.stream_video_incoming_call_notification_channel_description
                    } else {
                        R.string.stream_video_incoming_call_low_priority_notification_channel_description
                    },
                )
                importance = if (showAsHighPriority) {
                    NotificationManager.IMPORTANCE_HIGH
                } else {
                    NotificationManager.IMPORTANCE_LOW
                }
                this.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                this.setShowBadge(true)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.setAllowBubbles(true)
            }
        }
        return getNotification {
            priority = NotificationCompat.PRIORITY_HIGH
            setContentTitle(
                application.getString(R.string.stream_video_incoming_call_notification_title),
            )
            setContentText(callDisplayName)
            setChannelId(channelId)
            setOngoing(false)
            setContentIntent(fullScreenPendingIntent)
            setFullScreenIntent(fullScreenPendingIntent, true)
            setCategory(NotificationCompat.CATEGORY_CALL)
            addCallActions(acceptCallPendingIntent, rejectCallPendingIntent, callDisplayName)
        }
    }

    private fun getOutgoingCallNotification(
        outgoingCallPendingIntent: PendingIntent,
        endCallPendingIntent: PendingIntent,
        callDisplayName: String,
    ): Notification {
        val channelId = application.getString(
            R.string.stream_video_ongoing_call_notification_channel_id,
        )
        maybeCreateChannel(
            channelId = channelId,
            context = application,
            configure = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    description = application.getString(
                        R.string.stream_video_ongoing_call_notification_channel_description,
                    )
                }
            },
        )

        return getNotification {
            setContentTitle(
                application.getString(R.string.stream_video_outgoing_call_notification_title),
            )
            setContentText(callDisplayName)
            setChannelId(channelId)
            setOngoing(true)
            setContentIntent(outgoingCallPendingIntent)
            setCategory(NotificationCompat.CATEGORY_CALL)
            addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_close_clear_cancel,
                    application.getString(R.string.stream_video_call_notification_action_cancel),
                    endCallPendingIntent,
                ).build(),
            )
        }
    }

    override fun onNotification(callId: StreamCallId, callDisplayName: String) {
        val notificationId = callId.hashCode()
        intentResolver.searchNotificationCallPendingIntent(callId, notificationId)
            ?.let { notificationPendingIntent ->
                showNotificationCallNotification(
                    notificationPendingIntent,
                    callDisplayName,
                    notificationId,
                )
            } ?: logger.e { "Couldn't find any activity for $ACTION_NOTIFICATION" }
    }

    override fun onLiveCall(callId: StreamCallId, callDisplayName: String) {
        val notificationId = callId.hashCode()
        intentResolver.searchLiveCallPendingIntent(callId, notificationId)
            ?.let { liveCallPendingIntent ->
                showLiveCallNotification(
                    liveCallPendingIntent,
                    callDisplayName,
                    notificationId,
                )
            } ?: logger.e { "Couldn't find any activity for $ACTION_LIVE_CALL" }
    }

    override fun getOngoingCallNotification(
        callDisplayName: String?,
        callId: StreamCallId,
    ): Notification? {
        val notificationId = callId.hashCode() // Notification ID

        // Intents
        val ongoingCallIntent = intentResolver.searchOngoingCallPendingIntent(
            callId,
            notificationId,
        )
        val endCallIntent = intentResolver.searchEndCallPendingIntent(callId = callId)

        // Channel preparation
        val ongoingCallsChannelId = application.getString(
            R.string.stream_video_ongoing_call_notification_channel_id,
        )
        maybeCreateChannel(ongoingCallsChannelId, application) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                description =
                    application.getString(R.string.stream_video_ongoing_call_notification_channel_description)
            }
        }

        if (endCallIntent == null) {
            logger.e { "End call intent is null, not showing notification!" }
            return null
        }

        // Build notification
        return NotificationCompat.Builder(application, ongoingCallsChannelId)
            .setSmallIcon(notificationIconRes)
            .also {
                // If the intent is configured, clicking the notification will return to the call
                if (ongoingCallIntent != null) {
                    it.setContentIntent(ongoingCallIntent)
                } else {
                    logger.w { "Ongoing intent is null click on the ongoing call notification will not work." }
                }
            }
            .setContentTitle(
                application.getString(R.string.stream_video_ongoing_call_notification_title),
            )
            .setContentText(
                application.getString(R.string.stream_video_ongoing_call_notification_description),
            )
            .setAutoCancel(false)
            .setOngoing(true)
            .addHangupAction(endCallIntent, callDisplayName ?: callId.toString())
            .build()
    }

    private fun maybeCreateChannel(
        channelId: String,
        context: Context,
        configure: NotificationChannel.() -> Unit = {
        },
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                application.getString(
                    R.string.stream_video_ongoing_call_notification_channel_title,
                ),
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply(configure)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotificationCallNotification(
        notificationPendingIntent: PendingIntent,
        callDisplayName: String,
        notificationId: Int,
    ) {
        showNotification(notificationId) {
            setContentTitle("Incoming call")
            setContentText("$callDisplayName is calling you.")
            setContentIntent(notificationPendingIntent)
        }
    }

    private fun showLiveCallNotification(
        liveCallPendingIntent: PendingIntent,
        callDisplayName: String,
        notificationId: Int,
    ) {
        showNotification(notificationId) {
            setContentTitle("Live Call")
            setContentText("$callDisplayName is live now")
            setContentIntent(liveCallPendingIntent)
        }
    }

    private fun showIncomingCallNotification(
        fullScreenPendingIntent: PendingIntent,
        acceptCallPendingIntent: PendingIntent,
        rejectCallPendingIntent: PendingIntent,
        callDisplayName: String,
        notificationId: Int = INCOMING_CALL_NOTIFICATION_ID,
    ) {
        showNotification(notificationId) {
            priority = NotificationCompat.PRIORITY_HIGH
            setContentTitle("Incoming call")
            setContentText(callDisplayName)
            setOngoing(false)
            setContentIntent(fullScreenPendingIntent)
            setFullScreenIntent(fullScreenPendingIntent, true)
            setCategory(NotificationCompat.CATEGORY_CALL)
            addCallActions(acceptCallPendingIntent, rejectCallPendingIntent, callDisplayName)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(
        notificationId: Int,
        builder: NotificationCompat.Builder.() -> Unit,
    ) {
        val notification = getNotification(builder)
        notificationManager.notify(notificationId, notification)
    }

    private fun getNotification(
        builder: NotificationCompat.Builder.() -> Unit,
    ): Notification {
        return NotificationCompat.Builder(application, getChannelId())
            .setSmallIcon(notificationIconRes)
            .setAutoCancel(true)
            .apply(builder)
            .build()
    }

    private fun NotificationCompat.Builder.addHangupAction(
        rejectCallPendingIntent: PendingIntent,
        callDisplayName: String,
    ): NotificationCompat.Builder = apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setStyle(
                CallStyle.forOngoingCall(
                    Person.Builder()
                        .setName(callDisplayName)
                        .build(),
                    rejectCallPendingIntent,
                ),
            )
        } else {
            addAction(
                NotificationCompat.Action.Builder(
                    null,
                    application.getString(R.string.stream_video_call_notification_action_leave),
                    rejectCallPendingIntent,
                ).build(),
            )
        }
    }

    private fun NotificationCompat.Builder.addCallActions(
        acceptCallPendingIntent: PendingIntent,
        rejectCallPendingIntent: PendingIntent,
        callDisplayName: String,
    ): NotificationCompat.Builder = apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setStyle(
                CallStyle.forIncomingCall(
                    Person.Builder()
                        .setName(callDisplayName)
                        .build(),
                    rejectCallPendingIntent,
                    acceptCallPendingIntent,
                ),
            )
        } else {
            addAction(
                NotificationCompat.Action.Builder(
                    null,
                    application.getString(R.string.stream_video_call_notification_action_accept),
                    acceptCallPendingIntent,
                ).build(),
            )
            addAction(
                NotificationCompat.Action.Builder(
                    null,
                    application.getString(R.string.stream_video_call_notification_action_reject),
                    rejectCallPendingIntent,
                ).build(),
            )
        }
    }

    private fun isInForeground(): Boolean {
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        return (
            appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ||
                appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
            )
    }

    open fun getChannelId(): String = application.getString(
        R.string.stream_video_incoming_call_notification_channel_id,
    )

    open fun getChannelName(): String = application.getString(
        R.string.stream_video_incoming_call_notification_channel_title,
    )

    open fun getChannelDescription(): String = application.getString(
        R.string.stream_video_incoming_call_notification_channel_description,
    )

    companion object {

        internal val PENDING_INTENT_FLAG: Int by lazy {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        }
    }
}
