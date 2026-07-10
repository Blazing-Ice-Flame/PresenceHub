package com.ombryal.presencehub.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PresenceHubTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}
