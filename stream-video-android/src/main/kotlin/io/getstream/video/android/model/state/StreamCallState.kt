/*
 * Copyright (c) 2014-2022 Stream.io Inc. All rights reserved.
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

package io.getstream.video.android.model.state

import io.getstream.video.android.errors.VideoError
import io.getstream.video.android.model.CallUser
import io.getstream.video.android.model.IceServer
import io.getstream.video.android.model.SfuToken
import java.io.Serializable
import java.util.Date
import io.getstream.video.android.model.StreamCallCid as CallCid
import io.getstream.video.android.model.StreamCallId as CallId
import io.getstream.video.android.model.StreamCallType as CallType

/**
 * Represents possible state of [io.getstream.video.android.engine.StreamCallEngine].
 */
public sealed interface StreamCallState : Serializable {

    /**
     * Signifies there is no active call.
     */
    public object Idle : StreamCallState { override fun toString(): String = "Idle" }

    /**
     * Represents an active state.
     */
    public sealed class Active : StreamCallState {
        public abstract val callGuid: StreamCallGuid
        public abstract val callKind: StreamCallKind
    }

    /**
     * Represents a state which can be joined.
     */
    public sealed interface Joinable : StreamCallState {
        public val callGuid: StreamCallGuid
        public val callKind: StreamCallKind
    }

    /**
     * Signifies the caller starts the call.
     */
    public data class Starting(
        override val callGuid: StreamCallGuid,
        override val callKind: StreamCallKind,
        val memberUserIds: List<String>
    ) : Active(), Joinable

    public sealed class Started : Active() {
        public abstract val createdByUserId: String
        public abstract val broadcastingEnabled: Boolean
        public abstract val recordingEnabled: Boolean
        public abstract val createdAt: StreamDate
        public abstract val updatedAt: StreamDate
        public abstract val users: Map<String, CallUser>
    }

    /**
     * Signifies that the caller starts the call.
     */
    public data class Outgoing(
        override val callGuid: StreamCallGuid,
        override val callKind: StreamCallKind,
        override val createdByUserId: String,
        override val broadcastingEnabled: Boolean,
        override val recordingEnabled: Boolean,
        override val createdAt: StreamDate,
        override val updatedAt: StreamDate,
        override val users: Map<String, CallUser>,
        val acceptedByCallee: Boolean
    ) : Started(), Joinable

    public data class Incoming(
        override val callGuid: StreamCallGuid,
        override val callKind: StreamCallKind,
        override val createdByUserId: String,
        override val broadcastingEnabled: Boolean,
        override val recordingEnabled: Boolean,
        override val createdAt: StreamDate,
        override val updatedAt: StreamDate,
        override val users: Map<String, CallUser>,
        val acceptedByMe: Boolean,
    ) : Started(), Joinable

    public data class Joining(
        override val callGuid: StreamCallGuid,
        override val callKind: StreamCallKind,
        override val createdByUserId: String,
        override val broadcastingEnabled: Boolean,
        override val recordingEnabled: Boolean,
        override val createdAt: StreamDate,
        override val updatedAt: StreamDate,
        override val users: Map<String, CallUser>,
    ) : Started()

    public sealed class InCall : Started() {
        public abstract val callUrl: String
        public abstract val sfuToken: SfuToken
        public abstract val iceServers: List<IceServer>
        public abstract val sfuSessionId: StreamSfuSessionId
    }

    public data class Joined(
        override val callGuid: StreamCallGuid,
        override val sfuSessionId: StreamSfuSessionId,
        override val callKind: StreamCallKind,
        override val createdByUserId: String,
        override val broadcastingEnabled: Boolean,
        override val recordingEnabled: Boolean,
        override val createdAt: StreamDate,
        override val updatedAt: StreamDate,
        override val users: Map<String, CallUser>,
        override val callUrl: String,
        override val sfuToken: SfuToken,
        override val iceServers: List<IceServer>,
    ) : InCall()

    public data class Connecting(
        override val callGuid: StreamCallGuid,
        override val sfuSessionId: StreamSfuSessionId,
        override val callKind: StreamCallKind,
        override val createdByUserId: String,
        override val broadcastingEnabled: Boolean,
        override val recordingEnabled: Boolean,
        override val createdAt: StreamDate,
        override val updatedAt: StreamDate,
        override val users: Map<String, CallUser>,
        override val callUrl: String,
        override val sfuToken: SfuToken,
        override val iceServers: List<IceServer>,
    ) : InCall()

    public data class Connected(
        override val callGuid: StreamCallGuid,
        override val sfuSessionId: StreamSfuSessionId,
        override val callKind: StreamCallKind,
        override val createdByUserId: String,
        override val broadcastingEnabled: Boolean,
        override val recordingEnabled: Boolean,
        override val createdAt: StreamDate,
        override val updatedAt: StreamDate,
        override val users: Map<String, CallUser>,
        override val callUrl: String,
        override val sfuToken: SfuToken,
        override val iceServers: List<IceServer>,
    ) : InCall()

    public data class Drop(
        override val callGuid: StreamCallGuid,
        override val callKind: StreamCallKind,
        val reason: DropReason,
    ) : Active()
}

public sealed class DropReason : Serializable {
    public data class Timeout(val waitMillis: Long) : DropReason()
    public data class Failure(val error: VideoError) : DropReason()
    public data class Rejected(val byUserId: String) : DropReason()
    public data class Cancelled(val byUserId: String) : DropReason()
    public object Ended : DropReason() { override fun toString(): String = "Ended" }
}

public data class StreamCallGuid(
    val type: CallType,
    val id: CallId,
    val cid: CallCid
) : Serializable

public enum class StreamCallKind : Serializable {
    MEETING, RINGING
}

public sealed class StreamDate : Serializable {
    public data class Specified(val date: Date) : StreamDate() {
        public constructor(ts: Long) : this(Date(ts))
    }
    public object Undefined : StreamDate() { override fun toString(): String = "Undefined" }

    public companion object {
        public fun from(ts: Long): StreamDate = when (ts) {
            0L, -1L -> Undefined
            else -> Specified(ts)
        }

        public fun from(date: Date?): StreamDate = when (date) {
            null -> Undefined
            else -> Specified(date)
        }
    }
}

public sealed class StreamSfuSessionId {
    public object Undefined : StreamSfuSessionId() { override fun toString(): String = "Undefined" }
    public sealed class Specified : StreamSfuSessionId() {
        public abstract val value: String
    }
    public data class Requested(override val value: String) : Specified()
    public data class Confirmed(override val value: String) : Specified()
}

internal fun StreamCallState.Started.copy(
    createdByUserId: String = this.createdByUserId,
    broadcastingEnabled: Boolean = this.broadcastingEnabled,
    recordingEnabled: Boolean = this.recordingEnabled,
    createdAt: StreamDate = this.createdAt,
    updatedAt: StreamDate = this.updatedAt,
    users: Map<String, CallUser> = this.users,
): StreamCallState = when (this) {
    is StreamCallState.Joined -> copy(
        createdByUserId = createdByUserId,
        broadcastingEnabled = broadcastingEnabled,
        recordingEnabled = recordingEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt,
        users = users,
    )
    is StreamCallState.Connecting -> copy(
        createdByUserId = createdByUserId,
        broadcastingEnabled = broadcastingEnabled,
        recordingEnabled = recordingEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt,
        users = users,
    )
    is StreamCallState.Connected -> copy(
        createdByUserId = createdByUserId,
        broadcastingEnabled = broadcastingEnabled,
        recordingEnabled = recordingEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt,
        users = users,
    )
    is StreamCallState.Incoming -> copy(
        createdByUserId = createdByUserId,
        broadcastingEnabled = broadcastingEnabled,
        recordingEnabled = recordingEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt,
        users = users,
    )
    is StreamCallState.Joining -> copy(
        createdByUserId = createdByUserId,
        broadcastingEnabled = broadcastingEnabled,
        recordingEnabled = recordingEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt,
        users = users,
    )
    is StreamCallState.Outgoing -> copy(
        createdByUserId = createdByUserId,
        broadcastingEnabled = broadcastingEnabled,
        recordingEnabled = recordingEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt,
        users = users,
    )
}

internal fun StreamCallState.InCall.toConnecting() = StreamCallState.Connecting(
    callGuid = callGuid,
    callKind = callKind,
    callUrl = callUrl,
    createdByUserId = createdByUserId,
    broadcastingEnabled = broadcastingEnabled,
    recordingEnabled = recordingEnabled,
    createdAt = createdAt,
    updatedAt = updatedAt,
    users = users,
    iceServers = iceServers,
    sfuSessionId = sfuSessionId,
    sfuToken = sfuToken,
)

internal fun StreamCallState.InCall.toConnected() = StreamCallState.Connected(
    callGuid = callGuid,
    callKind = callKind,
    callUrl = callUrl,
    createdByUserId = createdByUserId,
    broadcastingEnabled = broadcastingEnabled,
    recordingEnabled = recordingEnabled,
    createdAt = createdAt,
    updatedAt = updatedAt,
    users = users,
    iceServers = iceServers,
    sfuSessionId = sfuSessionId,
    sfuToken = sfuToken,
)
