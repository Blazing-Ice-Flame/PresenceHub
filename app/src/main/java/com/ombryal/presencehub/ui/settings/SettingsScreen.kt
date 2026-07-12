package com.ombryal.presencehub.ui.settings

import android.content.Intent
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onUpdate: (SettingsUiState) -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit,
    onRefreshPlugins: () -> Unit
) {
    val context = LocalContext.current

    var generalExpanded by remember { mutableStateOf(true) }
    var presenceExpanded by remember { mutableStateOf(true) }
    var notificationsExpanded by remember { mutableStateOf(false) }
    var developerExpanded by remember { mutableStateOf(false) }
    var privacyExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF101623))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "General controls, Rich Presence, notifications, developer tools, and privacy.",
                        color = Color(0xFFB7B9C8)
                    )
                }
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Settings,
                title = "General",
                expanded = generalExpanded,
                onToggle = { generalExpanded = !generalExpanded }
            ) {
                SettingsSwitchRow(
                    label = "Auto start RPC",
                    description = "Start the RPC service automatically.",
                    checked = state.autoStartRpc,
                    onCheckedChange = { onUpdate(state.copy(autoStartRpc = it)) }
                )
                SettingsSwitchRow(
                    label = "Dynamic colors",
                    description = "Use system colors when available.",
                    checked = state.dynamicColors,
                    onCheckedChange = { onUpdate(state.copy(dynamicColors = it)) }
                )
                SettingsSwitchRow(
                    label = "Show plugin updates",
                    description = "Show update badges in the store.",
                    checked = state.showPluginUpdates,
                    onCheckedChange = { onUpdate(state.copy(showPluginUpdates = it)) }
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = onStartRpc, modifier = Modifier.weight(1f)) {
                        Text("Start RPC")
                    }
                    OutlinedButton(onClick = onStopRpc, modifier = Modifier.weight(1f)) {
                        Text("Stop RPC")
                    }
                }
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Tune,
                title = "Rich Presence",
                expanded = presenceExpanded,
                onToggle = { presenceExpanded = !presenceExpanded }
            ) {
                SettingsSwitchRow(
                    label = "Video title",
                    description = "Show the current video title.",
                    checked = state.showVideoTitle,
                    onCheckedChange = { onUpdate(state.copy(showVideoTitle = it)) }
                )
                SettingsSwitchRow(
                    label = "Channel name",
                    description = "Show the channel or creator name.",
                    checked = state.showChannelName,
                    onCheckedChange = { onUpdate(state.copy(showChannelName = it)) }
                )
                SettingsSwitchRow(
                    label = "Progress",
                    description = "Display the play progress.",
                    checked = state.showProgress,
                    onCheckedChange = { onUpdate(state.copy(showProgress = it)) }
                )
                SettingsSwitchRow(
                    label = "Elapsed time",
                    description = "Display elapsed time.",
                    checked = state.showElapsedTime,
                    onCheckedChange = { onUpdate(state.copy(showElapsedTime = it)) }
                )
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Notifications,
                title = "Notifications",
                expanded = notificationsExpanded,
                onToggle = { notificationsExpanded = !notificationsExpanded }
            ) {
                SettingsSwitchRow(
                    label = "Notification access",
                    description = "Allow YouTube detection from notifications.",
                    checked = state.notificationAccess,
                    onCheckedChange = { onUpdate(state.copy(notificationAccess = it)) }
                )
                SettingsSwitchRow(
                    label = "Background updates",
                    description = "Keep checking active playback in the background.",
                    checked = state.backgroundUpdates,
                    onCheckedChange = { onUpdate(state.copy(backgroundUpdates = it)) }
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    onClick = {
                        context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open Notification Access")
                }
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Devices,
                title = "Developer",
                expanded = developerExpanded,
                onToggle = { developerExpanded = !developerExpanded }
            ) {
                SettingsSwitchRow(
                    label = "Debug logging",
                    description = "Show extra logs while testing.",
                    checked = state.debugLogging,
                    onCheckedChange = { onUpdate(state.copy(debugLogging = it)) }
                )
                SettingsSwitchRow(
                    label = "Developer mode",
                    description = "Enable extra debug actions.",
                    checked = state.developerMode,
                    onCheckedChange = { onUpdate(state.copy(developerMode = it)) }
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = onRefreshPlugins, modifier = Modifier.weight(1f)) {
                        Text("Refresh Store")
                    }
                    OutlinedButton(onClick = onStartRpc, modifier = Modifier.weight(1f)) {
                        Text("Test RPC")
                    }
                }
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Lock,
                title = "Privacy",
                expanded = privacyExpanded,
                onToggle = { privacyExpanded = !privacyExpanded }
            ) {
                SettingsSwitchRow(
                    label = "Hide video titles",
                    description = "Mask exact titles in the preview.",
                    checked = state.hideTitles,
                    onCheckedChange = { onUpdate(state.copy(hideTitles = it)) }
                )
                SettingsSwitchRow(
                    label = "Hide channel names",
                    description = "Mask creator names in the preview.",
                    checked = state.hideChannels,
                    onCheckedChange = { onUpdate(state.copy(hideChannels = it)) }
                )
                SettingsSwitchRow(
                    label = "Clear history on exit",
                    description = "Remove local presence data when closing.",
                    checked = state.clearHistoryOnExit,
                    onCheckedChange = { onUpdate(state.copy(clearHistoryOnExit = it)) }
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Quick actions",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Use these when testing the build.",
                        color = Color(0xFFB7B9C8)
                    )
                    HorizontalDivider(color = Color(0xFF262B3A))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(onClick = onRefreshPlugins, modifier = Modifier.weight(1f)) {
                            Text("Refresh")
                        }
                        OutlinedButton(
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Permissions")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF8E96FF)
                )
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFFDFE3FF)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontWeight = FontWeight.SemiBold)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB7B9C8)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF2EE58D),
                checkedTrackColor = Color(0xFF1D5A38)
            )
        )
    }
}
