package com.ombryal.presencehub.ui.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.ui.account.AccountUiState

@Composable
fun SettingsScreen(
    state: SettingsUiState,
    accountState: AccountUiState,
    onUpdate: (SettingsUiState) -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit,
    onRefreshPlugins: () -> Unit
) {
    var accountsExpanded by remember { mutableStateOf(true) }
    var themeExpanded by remember { mutableStateOf(true) }

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
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manage your connected accounts and appearance.",
                        color = Color(0xFFB7B9C8)
                    )
                }
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Person,
                title = "Accounts",
                expanded = accountsExpanded,
                onToggle = { accountsExpanded = !accountsExpanded }
            ) {
                if (accountState.connected) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color(0xFF2EE58D),
                            modifier = Modifier.size(20.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = accountState.displayName,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = accountState.handle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB7B9C8)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Status: ${accountState.liveStatus}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9CA3B8)
                    )
                } else {
                    Text(
                        text = "No Discord account connected.",
                        color = Color(0xFFB7B9C8)
                    )
                }
            }
        }

        item {
            SettingsSectionCard(
                icon = Icons.Filled.Tune,
                title = "Theme",
                expanded = themeExpanded,
                onToggle = { themeExpanded = !themeExpanded }
            ) {
                val themeModes = ThemeMode.values()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    themeModes.forEach { mode ->
                        val selected = state.themeMode == mode
                        val label = when (mode) {
                            ThemeMode.SYSTEM -> "System"
                            ThemeMode.DARK -> "Dark"
                            ThemeMode.LIGHT -> "Light"
                            ThemeMode.GLASS -> "Glass"
                        }
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onUpdate(state.copy(themeMode = mode)) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selected) Color(0xFF8E96FF) else Color(0xFF1B2233)
                            )
                        ) {
                            Text(
                                text = label,
                                modifier = Modifier.padding(vertical = 10.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color = if (selected) Color.White else Color(0xFFB7B9C8),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111724))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF8E96FF)
                )
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFFDFE3FF)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    content()
                }
            }
        }
    }
}
