package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 18.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        // Blurred background layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .shadow(8.dp, RoundedCornerShape(cornerRadius), ambientColor = Color.Black.copy(alpha = 0.1f))
                .clip(RoundedCornerShape(cornerRadius))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(cornerRadius))
                .blur(4.dp)
        )
        // Content on top (sharp)
        Box(modifier = Modifier.matchParentSize()) {
            content()
        }
    }
}
