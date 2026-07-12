package com.ombryal.presencehub.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingGlassCapsule(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        TabItem(Routes.STORE, "Store", Icons.Default.Store),
        TabItem(Routes.HOME, "Home", Icons.Default.Home),
        TabItem(Routes.ACCOUNT, "Account", Icons.Default.Person)
    )

    val activeIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(0.76f)
            .padding(bottom = 12.dp)
            .height(56.dp)
    ) {
        val pillWidth = maxWidth * 0.28f
        val segmentWidth = maxWidth / 3
        val pillOffsetX by animateDpAsState(
            targetValue = segmentWidth * activeIndex + (segmentWidth - pillWidth) / 2,
            animationSpec = tween(durationMillis = 350)
        )

        // Outer glass container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Magenta.copy(alpha = 0.1f),
                    spotColor = Color.Magenta.copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC1A1A2E),
                            Color(0xCC10101A)
                        )
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp))
        ) {
            // Animated pill highlight
            Box(
                modifier = Modifier
                    .offset(x = pillOffsetX)
                    .padding(vertical = 6.dp)
                    .size(width = pillWidth, height = 44.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0x558E96FF),
                                Color(0x336366F1)
                            )
                        ),
                        RoundedCornerShape(20.dp)
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
            )

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val selected = index == activeIndex
                    Tab(
                        item = item,
                        selected = selected,
                        onClick = { onNavigate(item.route) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Tab(
    item: TabItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(56.dp)
            .clickable(onClick = onClick)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        // Crossfade between icon and label
        AnimatedContent(
            targetState = selected,
            transitionSpec = {
                fadeIn(animationSpec = tween(250)) togetherWith
                        fadeOut(animationSpec = tween(250))
            },
            label = "tab_content"
        ) { isSelected ->
            if (isSelected) {
                // Show label only, no icon
                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            } else {
                // Show icon only
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier.size(22.dp),
                    tint = Color(0x99B7B9C8)
                )
            }
        }
    }
}

private data class TabItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
