package com.ombryal.presencehub.rpc

import com.ombryal.presencehub.data.models.Presence
import com.ombryal.presencehub.utils.Logger

class DiscordClient {

    private var connected = false
    private var lastPresenceKey: String? = null

    fun connect() {
        connected = true
        Logger.d("Discord client connected")
    }

    fun disconnect() {
        connected = false
        lastPresenceKey = null
        Logger.d("Discord client disconnected")
    }

    fun isConnected(): Boolean = connected

    fun updatePresence(presence: Presence) {
        if (!connected) return

        val key = buildKey(presence)
        if (key == lastPresenceKey) return

        lastPresenceKey = key
        Logger.d("Updating Discord presence: ${presence.providerName} - ${presence.title}")
    }

    fun clearPresence() {
        if (!connected) return
        lastPresenceKey = null
        Logger.d("Clearing Discord presence")
    }

    private fun buildKey(presence: Presence): String {
        return listOf(
            presence.pluginId,
            presence.providerName,
            presence.title,
            presence.subtitle,
            presence.state,
            presence.startTimestamp?.toString(),
            presence.endTimestamp?.toString(),
            presence.buttonUrl
        ).joinToString("|")
    }
}
