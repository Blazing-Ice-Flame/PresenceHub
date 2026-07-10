package com.ombryal.presencehub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    pluginCount: Int,
    onOpenAccount: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAddApp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "PresenceHub",
            style = MaterialTheme.typography.headlineLarge
        )

        Card(
            colors = CardDefaults.cardColors()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Current Status", style = MaterialTheme.typography.titleMedium)
                Text(text = "Installed plugins: $pluginCount")
                Text(text = "Only YouTube is supported in v1.")
            }
        }

        Button(onClick = onOpenAddApp) { Text("Open Plugin Store") }
        Button(onClick = onOpenSettings) { Text("Settings") }
        Button(onClick = onOpenAccount) { Text("Account") }
        Button(onClick = onOpenAbout) { Text("About") }
    }
}
