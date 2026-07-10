package com.ombryal.presencehub.providers.youtube

import androidx.compose.runtime.Composable
import com.ombryal.presencehub.data.models.Presence
import com.ombryal.presencehub.plugins.PresencePlugin

class YouTubePlugin : PresencePlugin {

    override val pluginId: String = "youtube"
    override val displayName: String = "YouTube"
    override val version: String = "1.0.0"

    private var currentPresence: Presence? = null

    override fun isAvailable(): Boolean {
        return true
    }

    override fun start() {
        // Start track for youtube playback changes later
        // For now this is a stub ig
        currentPresence = Presence(
            pluginId = pluginId,
            providerName = displayName,
            title = "Waiting for YouTube",
            subtitle = "No video detected yet",
            state = "Idle",
            largeImageKey = "youtube_logo",
            smallImageKey = "youtube_idle"
        )
    }

    override fun stop() {
        currentPresence = null
    }

    override fun getCurrentPresence(): Presence? {
        return currentPresence
    }

    @Composable
    override fun SettingsScreen() {
        // youtube specific settings ui thing will go here.
    }
}
