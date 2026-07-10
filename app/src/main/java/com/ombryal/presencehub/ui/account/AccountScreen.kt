package com.ombryal.presencehub.ui.account

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

@Composable
fun AccountScreen(
    onBack: () -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Account", style = MaterialTheme.typography.headlineLarge)

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Discord connection status")
                Text(text = "Not connected")
            }
        }

        Button(onClick = onStartRpc) {
            Text("Start RPC Service")
        }

        Button(onClick = onStopRpc) {
            Text("Stop RPC Service")
        }

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
