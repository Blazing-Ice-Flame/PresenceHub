package com.ombryal.presencehub.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onAccountsClick: () -> Unit,
    onThemeClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero card – only "Settings" title
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(20.dp), ambientColor = Color.Magenta.copy(alpha = 0.1f))
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1B1E2E),
                                Color(0xFF141624)
                            )
                        )
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Search bar
        item {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }

        // Category rows
        item {
            SettingsRow(
                icon = Icons.Filled.Person,
                title = "Accounts",
                subtitle = "Manage connected Discord account and linked services",
                onClick = onAccountsClick
            )
        }
        item {
            SettingsRow(
                icon = Icons.Filled.Palette,
                title = "Appearance",
                subtitle = "Theme, colors, and UI style",
                onClick = onThemeClick
            )
        }
        item {
            SettingsRow(
                icon = Icons.Filled.Tune,
                title = "General",
                subtitle = "App behavior, startup, and basic options",
                onClick = { /* TODO */ }
            )
        }
        item {
            SettingsRow(
                icon = Icons.Filled.Backup,
                title = "Backup / Restore",
                subtitle = "Save or restore RPC presets",
                onClick = { /* TODO */ }
            )
        }
        item {
            SettingsRow(
                icon = Icons.Filled.Build,
                title = "Advanced",
                subtitle = "Developer or power-user options",
                onClick = { /* TODO */ }
            )
        }
        // About row (opens the new SettingsAboutScreen)
        item {
            SettingsRow(
                icon = Icons.Filled.Info,
                title = "About",
                subtitle = "Version info, licenses, and credits",
                onClick = onAboutClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(6.dp, RoundedCornerShape(16.dp), ambientColor = Color.Blue.copy(alpha = 0.1f))
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0x22FFFFFF),
                        Color(0x0AFFFFFF)
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color(0xFF8E96FF),
                modifier = Modifier.size(20.dp)
            )
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                singleLine = true,
                cursorBrush = SolidColor(Color(0xFF8E96FF)),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search settings...",
                            color = Color(0x88FFFFFF),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(4.dp, RoundedCornerShape(18.dp), ambientColor = Color.Magenta.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xCC121420))
            .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0x228E96FF),
                                Color(0x116366F1)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF8E96FF),
                    modifier = Modifier.size(22.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 15.sp
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9CA3B8),
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF6366F1),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
