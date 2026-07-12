package com.ombryal.presencehub.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.account.AccountUiState
import com.ombryal.presencehub.ui.theme.CircleContainer
import com.ombryal.presencehub.ui.theme.GlassContainer
import kotlin.math.min
import kotlin.math.max

@Composable
fun HomeScreen(
    storeState: PluginStoreState,
    accountState: AccountUiState,
    onOpenPluginDetails: (PluginRegistryEntry) -> Unit,
    onWatchOnYouTube: () -> Unit = {},
    onVisitChannel: () -> Unit = {}
) {
    val isActive = accountState.connected && accountState.activeProvider.isNotEmpty()
    val installedPlugins = storeState.plugins.filter { it.installed }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ---- Currently Playing ----
        GlassContainer(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 280.dp)   // fixed minimum height
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Cast,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Currently Playing",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                if (isActive) {
                    // App row
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        CircleContainer(modifier = Modifier.size(32.dp)) {
                            Text(
                                text = accountState.activeProvider.firstOrNull()?.uppercase() ?: "?",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = accountState.activeProvider,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.width(8.dp))
                        Surface(color = Color(0xFF2EE58D).copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp)) {
                            Text(
                                text = "Active",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Color(0xFF2EE58D),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = accountState.activeTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Unknown Creator",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Card(
                            modifier = Modifier.size(width = 80.dp, height = 60.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.Image,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    val timeParts = accountState.activeTime.split("/")
                    val current = timeParts.getOrNull(0)?.trim() ?: "00:00"
                    val total = timeParts.getOrNull(1)?.trim() ?: "00:00"
                    val progress = remember(current, total) {
                        val currentSec = timeToSeconds(current)
                        val totalSec = timeToSeconds(total)
                        if (totalSec > 0) min(max(currentSec / totalSec.toFloat(), 0f), 1f) else 0f
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(text = current, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                        Text(text = total, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = onWatchOnYouTube, modifier = Modifier.weight(1f)) {
                            Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Watch on YouTube")
                        }
                        OutlinedButton(onClick = onVisitChannel, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Filled.Person, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Visit Channel")
                        }
                    }
                } else {
                    Text(
                        text = "Connect a service to display rich presence here.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // ---- Downloaded Plugins (stretches to fill remaining space) ----
        GlassContainer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)   // stretch vertically
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Link, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Downloaded Plugins",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.height(8.dp))
                if (installedPlugins.isEmpty()) {
                    Text(
                        text = "No plugins installed yet. Visit the Plugin Store to get started.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    installedPlugins.forEach { plugin ->
                        PluginRow(plugin = plugin, onToggle = { /* TODO */ }, onArrowClick = { onOpenPluginDetails(plugin) })
                        if (plugin != installedPlugins.last()) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }
        }

        // Space for floating nav bar
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun PluginRow(plugin: PluginRegistryEntry, onToggle: (Boolean) -> Unit, onArrowClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleContainer(modifier = Modifier.size(40.dp)) {
            Text(
                text = plugin.name.firstOrNull()?.uppercase() ?: "?",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = plugin.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (plugin.installed) "Rich Presence Active" else "Not Connected",
                style = MaterialTheme.typography.bodySmall,
                color = if (plugin.installed) Color(0xFF2EE58D) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = plugin.installed,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MaterialTheme.colorScheme.primary)
        )
        IconButton(onClick = onArrowClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Details", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun timeToSeconds(time: String): Int {
    val parts = time.split(":")
    if (parts.size != 2) return 0
    val minutes = parts[0].toIntOrNull() ?: 0
    val seconds = parts[1].toIntOrNull() ?: 0
    return minutes * 60 + seconds
}
