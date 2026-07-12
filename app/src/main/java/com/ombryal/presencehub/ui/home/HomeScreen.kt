package com.ombryal.presencehub.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState

@Composable
fun HomeScreen(
    storeState: PluginStoreState,
    onOpenStore: () -> Unit,
    onOpenAccount: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val connectedApps = storeState.plugins.filter { it.installed }.ifEmpty {
        storeState.plugins.take(3)
    }

    var selectedPlugin by remember {
        mutableStateOf(
            connectedApps.firstOrNull() ?: storeState.plugins.firstOrNull()
        )
    }

    val activePlugin = selectedPlugin
    val topPadding by animateDpAsState(
        targetValue = if (activePlugin != null) 12.dp else 8.dp,
        animationSpec = tween(220),
        label = "topPadding"
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val compact = maxWidth < 360.dp
        val pagePadding = if (compact) 8.dp else 10.dp

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = pagePadding,
                end = pagePadding,
                top = topPadding,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(if (compact) 18.dp else 20.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFF101623)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(if (compact) 12.dp else 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Discord RPC",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Manage connected apps and the active Rich Presence layout.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB7B9C8)
                            )
                        }

                        TextButton(onClick = onOpenSettings) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = null
                            )
                        }

                        TextButton(onClick = onOpenStore) {
                            Icon(
                                imageVector = androidx.compose.material.icons.filled.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            item {
                SectionHeader("Currently Playing")
            }

            item {
                CurrentPlayingCard(
                    plugin = activePlugin,
                    compact = compact
                )
            }

            item {
                SectionHeader("Connected Apps")
            }

            if (connectedApps.isEmpty()) {
                item {
                    EmptyStateCard(
                        title = "No connected apps",
                        message = "Install the YouTube plugin from the store to start showing Rich Presence."
                    )
                }
            } else {
                item {
                    ConnectedAppStrip(
                        apps = connectedApps,
                        selectedPlugin = activePlugin,
                        onSelect = { selectedPlugin = it }
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = activePlugin != null,
                    enter = fadeIn(tween(180)) + expandVertically(tween(180)),
                    exit = fadeOut(tween(140)) + shrinkVertically(tween(140))
                ) {
                    if (activePlugin != null) {
                        AppConfigurationCard(
                            plugin = activePlugin,
                            compact = compact
                        )
                    }
                }
            }

            item {
                SectionHeader("Discord Preview")
            }

            item {
                DiscordPreviewCard(
                    plugin = activePlugin,
                    compact = compact
                )
            }

            item {
                Button(
                    onClick = onOpenStore,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text("Save")
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onOpenAccount,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Account")
                    }

                    OutlinedButton(
                        onClick = onOpenAbout,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("About")
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFEAF0FF),
        modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp)
    )
}

@Composable
private fun CurrentPlayingCard(
    plugin: PluginRegistryEntry?,
    compact: Boolean
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(if (compact) 18.dp else 20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF111724)
        )
    ) {
        if (plugin == null) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Nothing playing",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Open the plugin store and install YouTube to begin showing Rich Presence.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB7B9C8)
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFFF2D55),
                                    Color(0xFF8C4DFF)
                                )
                            )
                        )
                        .aspectRatio(1f)
                        .weight(0.34f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = plugin.name.take(2).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Column(
                    modifier = Modifier.weight(0.66f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = plugin.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (plugin.installed) "Enable Rich Presence" else "Not installed yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB7B9C8)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (plugin.installed) Icons.Filled.Check else Icons.Filled.Stop,
                            contentDescription = null,
                            tint = if (plugin.installed) Color(0xFF2EE58D) else Color(0xFF8A8A8A)
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = if (plugin.installed) "Ready" else "Waiting",
                            color = if (plugin.installed) Color(0xFF2EE58D) else Color(0xFF8A8A8A)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConnectedAppStrip(
    apps: List<PluginRegistryEntry>,
    selectedPlugin: PluginRegistryEntry?,
    onSelect: (PluginRegistryEntry) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        apps.forEach { plugin ->
            val selected = selectedPlugin?.pluginId == plugin.pluginId
            val bg = if (selected) Color(0xFF2B1F5E) else Color(0xFF111724)

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onSelect(plugin) },
                colors = CardDefaults.cardColors(containerColor = bg),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = plugin.name,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (plugin.installed) "Connected" else "Available",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (plugin.installed) Color(0xFF2EE58D) else Color(0xFFB7B9C8)
                    )
                }
            }
        }
    }
}

@Composable
private fun AppConfigurationCard(
    plugin: PluginRegistryEntry,
    compact: Boolean
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(if (compact) 18.dp else 20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF111724)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = plugin.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = plugin.installed,
                    onCheckedChange = null,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF2EE58D),
                        checkedTrackColor = Color(0xFF1D5A38)
                    )
                )
                Text(
                    text = "Enable Rich Presence",
                    style = MaterialTheme.typography.bodyMedium
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
                    .height(if (compact) 120.dp else 140.dp),
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

            Text(
                text = "Show",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFB7B9C8)
            )

            FeatureRow("Video Title", true)
            FeatureRow("Channel Name", true)
            FeatureRow("Progress", true)
            FeatureRow("Elapsed Time", true)

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Buttons",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFB7B9C8)
            )

            FeatureRow("Watch Video", true)
            FeatureRow("Visit Channel", true)
        }
    }
}

@Composable
private fun DiscordPreviewCard(
    plugin: PluginRegistryEntry?,
    compact: Boolean
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(if (compact) 18.dp else 20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF111724)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Discord Preview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF171A28))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = plugin?.name ?: "YouTube",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = if (plugin?.installed == true) "Watching video now" else "No active presence",
                        color = Color(0xFFB7B9C8)
                    )
                    Text(
                        text = "Video title • Channel • Progress",
                        color = Color(0xFF9CA3B8),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyStateCard(
    title: String,
    message: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF111724)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB7B9C8)
            )
        }
    }
}

@Composable
private fun FeatureRow(
    label: String,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Icon(
            imageVector = if (enabled) Icons.Filled.Check else Icons.Filled.Pause,
            contentDescription = null,
            tint = if (enabled) Color(0xFF2EE58D) else Color(0xFF8A8A8A)
        )
    }
}
