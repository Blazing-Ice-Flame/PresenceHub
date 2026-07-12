package com.ombryal.presencehub.ui.addapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginRegistryEntry

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
        PluginStoreCatalog.items.filter { item ->
            item.title.contains(searchQuery, ignoreCase = true) ||
                item.subtitle.contains(searchQuery, ignoreCase = true)
        }
    }

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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Plugin Store",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        },
                        placeholder = {
                            Text("Search...")
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onRefresh,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Refresh")
                        }

                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Back")
                        }
                    }
                }
            }
        }

        if (isLoading) {
            item {
                StatusCard(
                    icon = Icons.Filled.Download,
                    title = "Loading plugins",
                    subtitle = "Fetching the remote store index."
                )
            }
        }

        errorMessage?.let { error ->
            item {
                StatusCard(
                    icon = Icons.Filled.Warning,
                    title = "Store error",
                    subtitle = error
                )
            }
        }

        visibleItems.forEach { item ->
            val storeEntry = availablePlugins.firstOrNull { it.pluginId == item.pluginId }
            val available = storeEntry != null
            val installed = storeEntry?.installed == true

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = available) {
                            storeEntry?.let(onOpenDetails)
                        },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .background(
                                    color = Color(0xFF1B2233),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.title.take(1),
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8E96FF)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB7B9C8)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (installed) Icons.Filled.Check else Icons.Filled.Timer,
                                    contentDescription = null,
                                    tint = when {
                                        installed -> Color(0xFF2EE58D)
                                        available -> Color(0xFFF6C356)
                                        else -> Color(0xFF8A8A8A)
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
                                        available -> if (item.availableInV1) Color(0xFFF6C356) else Color(0xFF8A8A8A)
                                        else -> Color(0xFF8A8A8A)
                                    },
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = if (available) Color(0xFFDFE3FF) else Color(0xFF555A6A)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun StatusCard(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = Color(0xFF1B2233),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF8E96FF)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB7B9C8)
                )
            }
        }
    }
}
