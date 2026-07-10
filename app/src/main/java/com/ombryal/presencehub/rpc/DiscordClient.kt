package com.ombryal.presencehub.rpc

import com.ombryal.presencehub.data.models.Presence
import com.ombryal.presencehub.utils.Logger

class DiscordClient {

    private var connected = false

    fun connect() {
        connected = true
        Logger.d("Discord client connected")
    }

    fun disconnect() {
        connected = false
        Logger.d("Discord client disconnected")
    }

    fun isConnected(): Boolean = connected

    fun updatePresence(presence: Presence) {
        if (!connected) {
            Logger.d("Discord client not connected, skipping presence update")
            return
        }

        Logger.d("Updating Discord presence: ${presence.providerName} - ${presence.title}")
        // Real Discord RPC payload goes here later
    }

    fun clearPresence() {
        if (!connected) return
        Logger.d("Clearing Discord presence")
    }
}
