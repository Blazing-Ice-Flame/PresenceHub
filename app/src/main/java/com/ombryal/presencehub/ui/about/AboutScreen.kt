package com.ombryal.presencehub.ui.about

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
fun AboutScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "About", style = MaterialTheme.typography.headlineLarge)

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "PresenceHub")
                Text(text = "A modular Discord Rich Presence client for Android.")
                Text(text = "Built by Ombryal.")
            }
        }

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
