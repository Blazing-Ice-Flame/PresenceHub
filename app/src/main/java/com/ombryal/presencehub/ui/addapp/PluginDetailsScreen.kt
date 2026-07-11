package com.ombryal.presencehub.ui.addapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginRegistryEntry

@Composable
fun PluginDetailsScreen(
    plugin: PluginRegistryEntry,
    onInstall: (PluginRegistryEntry) -> Unit,
    onUninstall: (PluginRegistryEntry) -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = plugin.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Plugin ID: ${plugin.pluginId}")
                    Text(text = "Remote version: ${plugin.version}")
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = "API Version: ${plugin.apiVersion}")
                    Text(text = "Author: ${plugin.author}")
                    Text(text = if (plugin.verified) "Verified" else "Unverified")
                    Text(text = if (plugin.installed) "Installed" else "Not installed")
                    plugin.installedVersion?.let { Text(text = "Installed version: $it") }
                    if (plugin.updateAvailable) {
                        Text(text = "Update available")
                    }
                    plugin.description?.let { Text(text = it) }
                    Text(text = "Download URL: ${plugin.downloadUrl}")
                }
            }
        }

        item {
            if (plugin.installed) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (plugin.updateAvailable) {
                        Button(
                            onClick = { onInstall(plugin) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Update")
                        }
                    }

                    OutlinedButton(
                        onClick = { onUninstall(plugin) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Uninstall")
                    }
                }
            } else {
                Button(
                    onClick = { onInstall(plugin) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Install")
                }
            }
        }

        item {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back")
            }
        }
    }
}
