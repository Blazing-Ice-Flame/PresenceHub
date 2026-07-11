package com.ombryal.presencehub.ui.addapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun AddAppScreen(
    availablePlugins: List<PluginRegistryEntry>,
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit,
    onInstall: (PluginRegistryEntry) -> Unit,
    onOpenDetails: (PluginRegistryEntry) -> Unit,
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
                        text = "Plugin Store",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Install official plugins from the remote repository.")
                }
            }
        }

        item {
            Button(
                onClick = onRefresh,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Refresh Store")
            }
        }

        if (isLoading) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Loading plugins...",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        errorMessage?.let { error ->
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        if (availablePlugins.isEmpty() && !isLoading) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "No plugins found",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "The plugin repository may be empty or unavailable.")
                    }
                }
            }
        } else {
            items(availablePlugins) { plugin ->
                PluginCard(
                    plugin = plugin,
                    onOpenDetails = onOpenDetails,
                    onInstall = onInstall
                )
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

@Composable
private fun PluginCard(
    plugin: PluginRegistryEntry,
    onOpenDetails: (PluginRegistryEntry) -> Unit,
    onInstall: (PluginRegistryEntry) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = plugin.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(text = "Version: ${plugin.version}")
            Text(text = "API Version: ${plugin.apiVersion}")
            Text(text = if (plugin.verified) "Verified" else "Unverified")
            Text(text = if (plugin.installed) "Installed" else "Not installed")
            plugin.installedVersion?.let { Text(text = "Installed version: $it") }
            if (plugin.updateAvailable) {
                Text(text = "Update available")
            }
            plugin.description?.let { Text(text = it) }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onOpenDetails(plugin) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Details")
                }

                if (plugin.updateAvailable) {
                    Button(
                        onClick = { onInstall(plugin) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Update")
                    }
                } else if (!plugin.installed) {
                    Button(
                        onClick = { onInstall(plugin) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Install")
                    }
                }
            }
        }
    }
}
