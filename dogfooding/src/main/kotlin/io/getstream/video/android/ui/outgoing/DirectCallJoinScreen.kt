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

package io.getstream.video.android.ui.outgoing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.avatar.UserAvatar
import io.getstream.video.android.core.R
import io.getstream.video.android.model.User
import io.getstream.video.android.ui.theme.Colors
import io.getstream.video.android.ui.theme.StreamImageButton

@Composable
fun DirectCallJoinScreen(
    viewModel: DirectCallJoinViewModel = hiltViewModel(),
    navigateToDirectCall: (memberList: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) { viewModel.getGoogleAccounts() }

    VideoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(user = uiState.currentUser)

            Body(
                uiState = uiState,
                toggleUserSelection = { viewModel.toggleGoogleAccountSelection(it) },
                onStartCallClick = navigateToDirectCall,
            )
        }
    }
}

@Composable
private fun Header(user: User?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp) // Outer padding
            .padding(vertical = 12.dp), // Inner padding
        verticalArrangement = Arrangement.Center,
    ) {
        Row {
            user?.let {
                UserAvatar(
                    modifier = Modifier.size(24.dp),
                    userName = it.userNameOrId,
                    userImage = it.image,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                modifier = Modifier.weight(1f),
                color = Color.White,
                text = user?.name?.ifBlank { user.id }?.ifBlank { user.custom["email"] }.orEmpty(),
                maxLines = 1,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(io.getstream.video.android.R.string.select_direct_call_users),
            color = Color(0xFF979797),
            fontSize = 13.sp,
        )
    }
}

@Composable
private fun Body(
    uiState: DirectCallUiState,
    toggleUserSelection: (Int) -> Unit,
    onStartCallClick: (membersList: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                color = VideoTheme.colors.primaryAccent,
            )
        } else {
            uiState.googleAccounts?.let { users ->
                UserList(
                    entries = users,
                    onUserClick = { clickedIndex -> toggleUserSelection(clickedIndex) },
                )
                StreamImageButton( // Floating button
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp),
                    enabled = users.any { it.isSelected },
                    imageRes = R.drawable.stream_video_ic_call,
                    onClick = {
                        onStartCallClick(
                            users
                                .filter { it.isSelected }
                                .joinToString(separator = ",") { it.account.id ?: "" },
                        )
                    },
                )
            } ?: Text(
                text = stringResource(io.getstream.video.android.R.string.cannot_load_google_account_list),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
            )
        }
    }
}

@Composable
private fun UserList(entries: List<GoogleAccountUiState>, onUserClick: (Int) -> Unit) {
    LazyColumn {
        items(entries.size) { index ->
            with(entries[index]) {
                UserRow(
                    index = index,
                    name = account.name ?: "",
                    avatarUrl = account.photoUrl,
                    isSelected = isSelected,
                    onClick = { onUserClick(index) },
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun UserRow(
    index: Int,
    name: String,
    avatarUrl: String?,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(index) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            UserAvatar(
                modifier = Modifier.size(50.dp),
                userName = name,
                userImage = avatarUrl,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = name,
                color = Color.White,
                fontSize = 16.sp,
            )
        }
        RadioButton(
            selected = isSelected,
            modifier = Modifier.size(20.dp),
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = VideoTheme.colors.primaryAccent,
                unselectedColor = Color.LightGray,
            ),
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    VideoTheme {
        Header(user = User(name = "Very very very long user name here"))
    }
}
