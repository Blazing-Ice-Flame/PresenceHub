package com.ombryal.presencehub.ui.settings

import android.content.Intent
import android.provider.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit,
    onRefreshPlugins: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineLarge)

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "RPC Service")
                Text(text = "Start or stop the foreground service that keeps PresenceHub running.")
            }
        }

        Button(onClick = onStartRpc) {
            Text("Start RPC Service")
        }

        Button(onClick = onStopRpc) {
            Text("Stop RPC Service")
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Notification Access")
                Text(text = "Needed for YouTube detection through notifications.")
            }
        }

        Button(
            onClick = {
                context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        ) {
            Text("Enable Notification Access")
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Plugin Store")
                Text(text = "Refresh the remote plugin index from the plugin repository.")
            }
        }

        Button(onClick = onRefreshPlugins) {
            Text("Refresh Plugin Store")
        }

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
