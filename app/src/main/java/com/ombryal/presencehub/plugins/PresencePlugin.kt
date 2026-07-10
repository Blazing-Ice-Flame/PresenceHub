package com.ombryal.presencehub.plugins

import androidx.compose.runtime.Composable
import com.ombryal.presencehub.data.models.Presence

interface PresencePlugin {
    val pluginId: String
    val displayName: String
    val version: String

    fun isAvailable(): Boolean
    fun start()
    fun stop()

    fun onPoll() {
        // Default no-op
    }

    fun getCurrentPresence(): Presence?

    @Composable
    fun SettingsScreen()
}
