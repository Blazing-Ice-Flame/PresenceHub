package com.ombryal.presencehub.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsAboutScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF101623))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "PresenceHub - Discord RPC for Android",
                        color = Color(0xFFB7B9C8)
                    )
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Version 1.0.0",
                        color = Color.White
                    )
                    Text(
                        text = "Changelog:",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text("• First public launch", color = Color(0xFFE6EAF7))
                    Text("• YouTube plugin support", color = Color(0xFFE6EAF7))
                    Text("• Plugin store and settings shell", color = Color(0xFFE6EAF7))
                    Text("• Discord RPC foundation", color = Color(0xFFE6EAF7))
                }
            }
        }
    }
}
