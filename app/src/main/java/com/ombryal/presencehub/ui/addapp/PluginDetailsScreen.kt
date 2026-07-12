package com.ombryal.presencehub.ui.addapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginRegistryEntry

@Composable
fun PluginDetailsScreen(
    plugin: PluginRegistryEntry,
    onInstall: (PluginRegistryEntry) -> Unit,
    onUninstall: (PluginRegistryEntry) -> Unit,
    onBack: () -> Unit,
    onSave: (PluginRegistryEntry) -> Unit = {}
) {
    val isYouTube = plugin.pluginId == "youtube"

    var enablePresence by remember { mutableStateOf(true) }
    var showVideoTitle by remember { mutableStateOf(true) }
    var showChannelName by remember { mutableStateOf(true) }
    var showProgress by remember { mutableStateOf(true) }
    var showElapsedTime by remember { mutableStateOf(true) }
    var watchButton by remember { mutableStateOf(true) }
    var channelButton by remember { mutableStateOf(true) }

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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = plugin.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (plugin.installed) "Installed" else "Not installed",
                        color = if (plugin.installed) Color(0xFF2EE58D) else Color(0xFFF6C356)
                    )
                    Text(
                        text = plugin.description ?: "Plugin configuration",
                        color = Color(0xFFB7B9C8)
                    )
                }
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Enable Rich Presence",
                            fontWeight = FontWeight.Bold
                        )
                        Switch(
                            checked = enablePresence,
                            onCheckedChange = { enablePresence = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF2EE58D),
                                checkedTrackColor = Color(0xFF1D5A38)
                            )
                        )
                    }

                    Text(
                        text = "Large Image",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFB7B9C8)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF171A28))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Thumbnail",
                                color = Color(0xFF9CA3B8)
                            )
                        }
                    }
                }
            }
        }

        if (isYouTube) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Show",
                            fontWeight = FontWeight.Bold
                        )

                        ToggleRow("Video Title", showVideoTitle) { showVideoTitle = it }
                        ToggleRow("Channel Name", showChannelName) { showChannelName = it }
                        ToggleRow("Progress", showProgress) { showProgress = it }
                        ToggleRow("Elapsed Time", showElapsedTime) { showElapsedTime = it }

                        HorizontalDivider(color = Color(0xFF262B3A))

                        Text(
                            text = "Buttons",
                            fontWeight = FontWeight.Bold
                        )

                        ToggleRow("Watch Video", watchButton) { watchButton = it }
                        ToggleRow("Visit Channel", channelButton) { channelButton = it }
                    }
                }
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "No custom settings yet",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "This plugin will use the shared layout until its own settings are added.",
                            color = Color(0xFFB7B9C8)
                        )
                    }
                }
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Discord Preview",
                        fontWeight = FontWeight.Bold
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF171A28))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            listOf(Color(0xFFFF2D55), Color(0xFF8C4DFF))
                                        ),
                                        shape = RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = plugin.name,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (enablePresence) "Watching a video" else "Presence disabled",
                                    color = Color(0xFFB7B9C8)
                                )
                                Text(
                                    text = "Video title • Channel name • Progress",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF9CA3B8)
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { onSave(plugin) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Save")
                }

                if (plugin.installed) {
                    OutlinedButton(
                        onClick = { onUninstall(plugin) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Uninstall")
                    }
                } else {
                    OutlinedButton(
                        onClick = { onInstall(plugin) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Install")
                    }
                }

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun ToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = if (checked) Color(0xFF2EE58D) else Color(0xFF8A8A8A)
            )
            Text(text = label)
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
