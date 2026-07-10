package com.ombryal.presencehub.plugins

class PluginLoader(
    private val trustManager: PluginTrustManager = PluginTrustManager()
) {

    fun install(entry: PluginRegistryEntry): Boolean {
        return trustManager.canInstall(entry)
    }

    fun uninstall(pluginId: String): Boolean {
        return pluginId.isNotBlank()
    }
}
