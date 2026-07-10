package com.ombryal.presencehub.ui.addapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Plugin Store", style = MaterialTheme.typography.headlineLarge)

        Button(onClick = onRefresh) {
            Text("Refresh Store")
        }

        if (isLoading) {
            Text(text = "Loading plugins...")
        }

        errorMessage?.let {
            Text(text = it)
        }

        availablePlugins.forEach { plugin ->
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = plugin.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Version: ${plugin.version}")
                    Text(text = if (plugin.verified) "Verified" else "Unverified")
                    Text(text = if (plugin.installed) "Installed" else "Not installed")
                    plugin.installedVersion?.let { Text(text = "Installed version: $it") }
                    if (plugin.updateAvailable) {
                        Text(text = "Update available")
                    }
                    plugin.description?.let { Text(text = it) }

                    Button(onClick = { onOpenDetails(plugin) }) {
                        Text("Details")
                    }

                    if (plugin.updateAvailable) {
                        Button(onClick = { onInstall(plugin) }) {
                            Text("Update")
                        }
                    } else if (!plugin.installed) {
                        Button(onClick = { onInstall(plugin) }) {
                            Text("Install")
                        }
                    }
                }
            }
        }

        if (!isLoading && availablePlugins.isEmpty()) {
            Text(text = "No plugins loaded yet.")
        }

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
