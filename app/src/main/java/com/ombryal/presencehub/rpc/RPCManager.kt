package com.ombryal.presencehub.rpc

import com.ombryal.presencehub.data.models.Presence
import com.ombryal.presencehub.plugins.PluginManager

class RPCManager(
    private val pluginManager: PluginManager
) {
    private val discordClient = DiscordClient()
    private var currentPresence: Presence? = null

    fun connect() {
        discordClient.connect()
    }

    fun disconnect() {
        discordClient.disconnect()
    }

    fun refreshPresence() {
        val newPresence = pluginManager.getCurrentPresence()

        if (newPresence != currentPresence) {
            currentPresence = newPresence

            if (newPresence != null) {
                discordClient.updatePresence(newPresence)
            } else {
                discordClient.clearPresence()
            }
        }
    }

    fun clearPresence() {
        currentPresence = null
        discordClient.clearPresence()
    }
}
