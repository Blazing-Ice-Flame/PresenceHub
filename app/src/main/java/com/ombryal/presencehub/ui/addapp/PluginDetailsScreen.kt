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
fun PluginDetailsScreen(
    plugin: PluginRegistryEntry,
    onInstall: (PluginRegistryEntry) -> Unit,
    onUninstall: (PluginRegistryEntry) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = plugin.name, style = MaterialTheme.typography.headlineLarge)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Plugin ID: ${plugin.pluginId}")
                Text(text = "Remote Version: ${plugin.version}")
                plugin.installedVersion?.let { Text(text = "Installed Version: $it") }
                Text(text = "API Version: ${plugin.apiVersion}")
                Text(text = if (plugin.verified) "Verified" else "Unverified")
                Text(text = if (plugin.installed) "Installed" else "Not installed")
                if (plugin.updateAvailable) {
                    Text(text = "Update available")
                }
                plugin.description?.let { Text(text = it) }
                Text(text = "Download URL: ${plugin.downloadUrl}")
            }
        }

        if (plugin.installed) {
            if (plugin.updateAvailable) {
                Button(onClick = { onInstall(plugin) }) {
                    Text(text = "Update")
                }
            }

            Button(onClick = { onUninstall(plugin) }) {
                Text(text = "Uninstall")
            }
        } else {
            Button(onClick = { onInstall(plugin) }) {
                Text(text = "Install")
            }
        }

        Button(onClick = onBack) {
            Text(text = "Back")
        }
    }
}
