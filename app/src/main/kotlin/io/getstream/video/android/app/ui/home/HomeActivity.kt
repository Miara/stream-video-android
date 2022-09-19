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

package io.getstream.video.android.app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.video.android.app.VideoApp
import io.getstream.video.android.app.model.HomeScreenOption
import io.getstream.video.android.app.ui.call.CallActivity
import io.getstream.video.android.app.ui.components.UserList
import io.getstream.video.android.app.utils.getUsers
import io.getstream.video.android.compose.ui.IncomingCallActivity
import io.getstream.video.android.events.CallCreatedEvent
import io.getstream.video.android.events.VideoEvent
import io.getstream.video.android.model.UserCredentials
import io.getstream.video.android.socket.SocketListener

class HomeActivity : AppCompatActivity() {

    private val selectedOption: MutableState<HomeScreenOption> =
        mutableStateOf(HomeScreenOption.CREATE_CALL)

    private val participantsOptions: MutableState<List<UserCredentials>> =
        mutableStateOf(
            getUsers().filter {
                it.id != VideoApp.videoClient.getUser().id
            }
        )

    private val callIdState: MutableState<String> = mutableStateOf("call:123")

    private val socketListener = object : SocketListener {
        override fun onEvent(event: VideoEvent) {
            if (event is CallCreatedEvent) {
                startActivity(IncomingCallActivity.getLaunchIntent(this@HomeActivity))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VideoApp.videoClient.addSocketListener(socketListener)
        setContent {
            HomeScreen()
        }
    }

    override fun onDestroy() {
        VideoApp.videoClient.removeSocketListener(socketListener)
        super.onDestroy()
    }

    @Composable
    private fun HomeScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                CallOptions()

                val selectedOption by remember { selectedOption }

                if (selectedOption == HomeScreenOption.CREATE_CALL) {
                    CreateCallContent()
                } else {
                    JoinCallContent()
                }
            }
        }
    }

    @Composable
    fun ColumnScope.CreateCallContent() {
        CallIdInput()

        ParticipantsOptions()

        val isDataValid = callIdState.value.isNotBlank()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(CenterHorizontally),
            enabled = isDataValid,
            onClick = {
                navigateToCall(
                    callId = callIdState.value,
                    participants = participantsOptions.value.filter { it.isSelected }.map { it.id }
                )
            },
            content = {
                Text(text = "Create call")
            }
        )
    }

    @Composable
    fun ColumnScope.JoinCallContent() {
        CallIdInput()

        val isDataValid = callIdState.value.isNotBlank()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(CenterHorizontally),
            enabled = isDataValid,
            onClick = { navigateToCall(callId = callIdState.value) },
            content = {
                Text(text = "Join call")
            }
        )
    }

    @Composable
    fun CallIdInput() {
        val inputState by remember { callIdState }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = inputState,
            onValueChange = { input ->
                callIdState.value = input
            },
            label = {
                Text(text = "Enter the call ID")
            }
        )
    }

    @Composable
    fun CallOptions() {
        val selectedOption by remember { selectedOption }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectedOption == HomeScreenOption.CREATE_CALL) MaterialTheme.colors.primary else Color.LightGray,
                    contentColor = Color.White
                ),
                onClick = { this@HomeActivity.selectedOption.value = HomeScreenOption.CREATE_CALL },
                content = {
                    Text(text = "Create Call")
                }
            )

            Button(
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectedOption == HomeScreenOption.JOIN_CALL) MaterialTheme.colors.primary else Color.LightGray,
                    contentColor = Color.White
                ),
                onClick = { this@HomeActivity.selectedOption.value = HomeScreenOption.JOIN_CALL },
                content = {
                    Text(text = "Join Call")
                }
            )
        }
    }

    @Composable
    fun ColumnScope.ParticipantsOptions() {
        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = "Select other participants",
            fontSize = 18.sp
        )

        val users by remember { participantsOptions }

        UserList(userItems = users, onClick = { user ->
            toggleSelectState(user, users)
        })
    }

    private fun navigateToCall(
        callId: String,
        participants: List<String> = emptyList()
    ) {
        val intent = CallActivity.getIntent(this, callId, participants)
        startActivity(intent)
    }

    private fun toggleSelectState(user: UserCredentials, users: List<UserCredentials>) {
        val currentUsers = users.toMutableList()
        val selectedUser = currentUsers.firstOrNull { it.id == user.id }

        if (selectedUser != null) {
            val index = currentUsers.indexOf(selectedUser)
            val updated = selectedUser.copy(isSelected = !selectedUser.isSelected)

            currentUsers.removeAt(index)
            currentUsers.add(index, updated)

            participantsOptions.value = currentUsers
        }
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}
