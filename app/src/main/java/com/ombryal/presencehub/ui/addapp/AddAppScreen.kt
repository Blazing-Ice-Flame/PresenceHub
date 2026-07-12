package com.ombryal.presencehub.ui.addapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.ui.theme.CircleContainer
import com.ombryal.presencehub.ui.theme.GlassContainer

@Composable
fun AddAppScreen(
    availablePlugins: List<PluginRegistryEntry>,
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit,
    onInstall: (PluginRegistryEntry) -> Unit,
    onOpenDetails: (PluginRegistryEntry) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val visibleItems = remember(searchQuery) {
        PluginStoreCatalog.items.filter { it.title.contains(searchQuery, ignoreCase = true) || it.subtitle.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ---- Search bar (glass) ----
        GlassContainer(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    placeholder = { Text("Search...") }
                )
            }
        }

        // ---- Plugin list (stretches) ----
        GlassContainer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) { CircularProgressIndicator() }
                } else if (errorMessage != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) { Text(text = errorMessage, color = MaterialTheme.colorScheme.error) }
                } else if (visibleItems.isEmpty()) {
                    Text(
                        text = "No plugins match your search.",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    visibleItems.forEach { item ->
                        val storeEntry = availablePlugins.firstOrNull { it.pluginId == item.pluginId }
                        val available = storeEntry != null
                        val installed = storeEntry?.installed == true

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = available) { storeEntry?.let(onOpenDetails) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CircleContainer(modifier = Modifier.size(38.dp)) {
                                Text(
                                    text = item.title.firstOrNull()?.uppercase() ?: "?",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = item.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(
                                        imageVector = if (installed) Icons.Filled.Check else Icons.Filled.Timer,
                                        contentDescription = null,
                                        tint = when {
                                            installed -> Color(0xFF2EE58D)
                                            available -> Color(0xFFF6C356)
                                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = when {
                                            installed -> "Installed"
                                            available -> if (item.availableInV1) "Available" else "Coming soon"
                                            else -> "Coming soon"
                                        },
                                        color = when {
                                            installed -> Color(0xFF2EE58D)
                                            available -> if (item.availableInV1) Color(0xFFF6C356) else MaterialTheme.colorScheme.onSurfaceVariant
                                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = if (available) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(80.dp)) // space for floating nav
    }
}
