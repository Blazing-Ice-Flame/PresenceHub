package com.ombryal.presencehub.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toPx

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
            .fillMaxWidth(0.88f)
            .padding(bottom = 24.dp)
            .height(72.dp)
    ) {
        val pillWidth = maxWidth * 0.28f
        val segmentWidth = maxWidth / 3
        val pillOffsetX by animateDpAsState(
            targetValue = segmentWidth * activeIndex + (segmentWidth - pillWidth) / 2,
            animationSpec = tween(durationMillis = 350)
        )

        // Glass container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(36.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .clip(RoundedCornerShape(36.dp))
                .background(Color(0x26FFFFFF))
                .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(36.dp))
        ) {
            // Active pill highlight – glass‑styled radial glow
            Box(
                modifier = Modifier
                    .offset(x = pillOffsetX)
                    .fillMaxHeight()
                    .width(pillWidth)
                    .clip(RoundedCornerShape(36.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0x1A8E96FF), // faint purple
                                Color(0x0FFFFFFF)
                            ),
                            center = Offset(pillWidth.toPx() / 2, 36.dp.toPx()),
                            radius = pillWidth.toPx() * 0.8f
                        )
                    )
                    .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(36.dp))
            )

            // Tabs row
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val selected = index == activeIndex
                    TabItemContent(
                        item = item,
                        selected = selected,
                        segmentWidth = segmentWidth,
                        onClick = { onNavigate(item.route) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TabItemContent(
    item: TabItem,
    selected: Boolean,
    segmentWidth: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit
) {
    val iconOffsetY by animateDpAsState(
        targetValue = if (selected) -10.dp else 0.dp,
        animationSpec = tween(300)
    )
    val textOffsetY by animateDpAsState(
        targetValue = if (selected) 10.dp else 30.dp,
        animationSpec = tween(300)
    )
    val textAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .width(segmentWidth)
            .height(72.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier
                .size(22.dp)
                .offset(y = iconOffsetY),
            tint = if (selected) Color(0xFFE0E7FF) else Color(0x99B7B9C8)
        )

        Text(
            text = item.label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) Color(0xFFE0E7FF) else Color.Transparent,
            modifier = Modifier
                .offset(y = textOffsetY)
                .graphicsLayer { alpha = textAlpha }
        )
    }
}

private data class TabItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
