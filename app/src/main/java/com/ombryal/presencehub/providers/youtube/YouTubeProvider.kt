package com.ombryal.presencehub.providers.youtube

import androidx.compose.runtime.Composable
import com.ombryal.presencehub.data.models.Presence
import com.ombryal.presencehub.plugins.PresencePlugin

class YouTubeProvider : PresencePlugin {

    override val pluginId: String = "youtube"
    override val displayName: String = "YouTube"
    override val version: String = "1.0.0"

    private val detector = YouTubeDetector()
    private var currentPresence: Presence? = null
    private var running = false

    override fun isAvailable(): Boolean = true

    override fun start() {
        running = true
        onPoll()
    }

    override fun stop() {
        running = false
        currentPresence = null
    }

    override fun onPoll() {
        if (!running) return

        val mediaData = detector.detect()
        currentPresence = mediaData?.let { YouTubePresenceMapper.map(it) }
    }

    override fun getCurrentPresence(): Presence? {
        return currentPresence
    }

    @Composable
    override fun SettingsScreen() {
        // I'm gonna build the UI later for setting 
    }
}
