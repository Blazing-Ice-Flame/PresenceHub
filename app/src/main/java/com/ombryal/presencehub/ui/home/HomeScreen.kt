package com.ombryal.presencehub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginStoreState

@Composable
fun HomeScreen(
    storeState: PluginStoreState,
    onOpenStore: () -> Unit,
    onOpenAccount: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val installedCount = storeState.plugins.count { it.installed }
    val active = storeState.plugins.firstOrNull { it.installed }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Discord RPC",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(onClick = onOpenSettings) { Text("⚙") }
                        Button(onClick = onOpenStore) { Text("➕") }
                    }
                }
            }
        }

        item {
            DashboardSection("Currently Playing") {
                if (active != null) {
                    Text(text = active.name, fontWeight = FontWeight.Bold)
                    Text(text = active.description ?: "Ready to show Rich Presence")
                    Text(text = if (active.installed) "Connected" else "Waiting")
                } else {
                    Text(text = "Nothing active yet")
                    Text(text = "Install and enable YouTube to begin.")
                }
            }
        }

        item {
            DashboardSection("Connected Apps") {
                Text(text = "Installed: $installedCount")
                if (storeState.plugins.isEmpty()) {
                    Text(text = "No plugins loaded")
                } else {
                    storeState.plugins.take(3).forEach { plugin ->
                        Text(text = "• ${plugin.name}")
                    }
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = onOpenAccount, modifier = Modifier.weight(1f)) {
                    Text("Account")
                }
                OutlinedButton(onClick = onOpenAbout, modifier = Modifier.weight(1f)) {
                    Text("About")
                }
            }
        }
    }
}

@Composable
private fun DashboardSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
            content()
        }
    }
}
