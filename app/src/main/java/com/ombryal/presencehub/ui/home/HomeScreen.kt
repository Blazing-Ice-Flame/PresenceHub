package com.ombryal.presencehub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.theme.CircleContainer

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
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        item {
            DashboardSection("Currently Playing") {
                if (active != null) {
                    Text(
                        text = active.name,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = active.description ?: "Ready to show Rich Presence",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (active.installed) "Connected" else "Waiting",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "Nothing active yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Install and enable YouTube to begin.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            DashboardSection("Connected Apps") {
                Text(
                    text = "Installed: $installedCount",
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (storeState.plugins.isEmpty()) {
                    Text(
                        text = "No plugins loaded",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    storeState.plugins.take(3).forEach { plugin ->
                        Text(
                            text = "• ${plugin.name}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            content()
        }
    }
}
