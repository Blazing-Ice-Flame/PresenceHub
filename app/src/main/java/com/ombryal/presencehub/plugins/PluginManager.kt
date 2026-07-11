package com.ombryal.presencehub.plugins

import com.ombryal.presencehub.data.models.Presence

class PluginManager(
    private val installedPluginRegistry: InstalledPluginRegistry? = null
) {
    private val installedPlugins = mutableMapOf<String, PresencePlugin>()
    private var activePluginId: String? = null

    fun registerPlugin(plugin: PresencePlugin) {
        installedPlugins[plugin.pluginId] = plugin
        if (activePluginId == null) {
            activePluginId = plugin.pluginId
        }
    }

    fun unregisterPlugin(pluginId: String) {
        installedPlugins[pluginId]?.stop()
        installedPlugins.remove(pluginId)

        if (activePluginId == pluginId) {
            activePluginId = installedPlugins.keys.firstOrNull()
        }
    }

    fun setActivePlugin(pluginId: String) {
        if (installedPlugins.containsKey(pluginId)) {
            activePluginId = pluginId
        }
    }

    fun getActivePlugin(): PresencePlugin? {
        return activePluginId?.let { installedPlugins[it] }
    }

    fun getInstalledPlugins(): List<PresencePlugin> {
        return installedPlugins.values.toList()
    }

    fun getCurrentPresence(): Presence? {
        return getActivePlugin()?.getCurrentPresence()
    }

    fun startActivePlugin() {
        getActivePlugin()?.start()
    }

    fun stopAllPlugins() {
        installedPlugins.values.forEach { it.stop() }
    }
}
