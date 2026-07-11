package com.ombryal.presencehub.ui.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AccountScreen(
    state: AccountUiState,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val compact = maxWidth < 360.dp
        val horizontalPadding = if (compact) 14.dp else 18.dp
        val cardRadius = if (compact) 20.dp else 24.dp
        val avatarSize = if (compact) 68.dp else 74.dp

        val topAlpha by animateFloatAsState(
            targetValue = if (state.connected) 1f else 0.92f,
            animationSpec = tween(280),
            label = "topAlpha"
        )

        val topOffset by animateDpAsState(
            targetValue = if (state.connected) 0.dp else 4.dp,
            animationSpec = tween(280),
            label = "topOffset"
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = horizontalPadding,
                end = horizontalPadding,
                top = 14.dp,
                bottom = 18.dp
            ),
            verticalArrangement = Arrangement.spacedBy(if (compact) 12.dp else 14.dp)
        ) {
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = topOffset),
                    shape = RoundedCornerShape(cardRadius),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFF101623)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF5B3DF5).copy(alpha = 0.18f),
                                        Color(0xFF7A4DFF).copy(alpha = 0.05f),
                                        Color.Transparent
                                    )
                                )
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(if (compact) 14.dp else 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(if (compact) 12.dp else 14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(avatarSize)
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                Color(0xFF8A6BFF),
                                                Color(0xFF2B2448)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFFD8CBFF),
                                    modifier = Modifier.size(if (compact) 34.dp else 38.dp)
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "VoidDev",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "voiddev_#1337",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFB0B3C2)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (state.connected) Color(0xFF2EE58D) else Color(0xFF8A8A8A)
                                            )
                                    )

                                    Text(
                                        text = if (state.connected) "Connected" else "Not connected",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (state.connected) Color(0xFF2EE58D) else Color(0xFF8A8A8A)
                                    )
                                }

                                AnimatedVisibility(
                                    visible = state.connected,
                                    enter = fadeIn(tween(220)) + slideInVertically(tween(220)) { it / 2 },
                                    exit = fadeOut(tween(180)) + slideOutVertically(tween(180)) { it / 2 }
                                ) {
                                    Text(
                                        text = state.statusMessage,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFB7B9C8),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(if (compact) 40.dp else 42.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF23283A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color(0xFFDFE3FF),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                if (state.connected) {
                    SectionCard(
                        title = "Rich Presence Status",
                        trailingText = state.liveStatus,
                        cardRadius = cardRadius,
                        containerColor = Color(0xFF111724)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(if (compact) 96.dp else 108.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        brush = Brush.linearGradient(
                                            listOf(
                                                Color(0xFFB4235A),
                                                Color(0xFF6D2DFF)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(42.dp)
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = state.activeProvider,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = state.activeTitle,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color(0xFFE7EAF4)
                                )
                                Text(
                                    text = state.activeTime,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFFB7BAD0)
                                )
                            }
                        }
                    }
                }
            }

            item {
                SectionCard(
                    title = "Connection",
                    cardRadius = cardRadius,
                    containerColor = Color(0xFF111724)
                ) {
                    Text(
                        text = "Maintain a stable connection to keep RPC active.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB7B9C8)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onStartRpc,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Reconnect")
                        }

                        OutlinedButton(
                            onClick = onStopRpc,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Link,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Disconnect")
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Try reconnecting if Discord stops updating.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9CA3B8)
                    )
                }
            }

            item {
                SectionCard(
                    title = "Permissions",
                    subtitle = "This app is allowed to:",
                    cardRadius = cardRadius,
                    containerColor = Color(0xFF111724)
                ) {
                    state.permissions.forEach { permission ->
                        PermissionRow(permission.label, permission.granted)
                    }
                }
            }

            item {
                if (state.connected) {
                    SectionCard(
                        title = "RPC Preview",
                        cardRadius = cardRadius,
                        containerColor = Color(0xFF111724)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF171A28))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(
                                            brush = Brush.linearGradient(
                                                listOf(
                                                    Color(0xFFFF2D55),
                                                    Color(0xFF8C4DFF)
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "YT",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = state.activeProvider.uppercase(),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFFB7B9C8)
                                    )
                                    Text(
                                        text = state.activeTitle,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Live preview of the active plugin",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF9CA3B8)
                                    )
                                }
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    trailingText: String? = null,
    subtitle: String? = null,
    cardRadius: androidx.compose.ui.unit.Dp,
    containerColor: Color,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cardRadius),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (trailingText != null) {
                    Text(
                        text = trailingText,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF32D07F)
                    )
                }
            }

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB7B9C8)
                )
            }

            content()
        }
    }
}

@Composable
private fun PermissionRow(
    text: String,
    granted: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E2436)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                tint = Color(0xFF8E96FF),
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFE6EAF7)
        )

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = if (granted) Color(0xFF2EE58D) else Color(0xFF8A8A8A),
            modifier = Modifier.size(18.dp)
        )
    }
}
