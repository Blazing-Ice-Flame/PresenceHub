package com.ombryal.presencehub.plugins

class PluginInstallManager(
    private val trustManager: PluginTrustManager = PluginTrustManager(),
    private val downloadManager: PluginDownloadManager = PluginDownloadManager(),
    private val installedPluginRegistry: InstalledPluginRegistry? = null
) {

    fun install(entry: PluginRegistryEntry): Boolean {
        if (!trustManager.canInstall(entry)) return false

        val bytes = downloadManager.downloadBytes(entry.downloadUrl) ?: return false

        val publicKeyPem = ""

        val verified = trustManager.verifyDownloadedPlugin(
            pluginBytes = bytes,
            entry = entry,
            publicKeyPem = publicKeyPem
        )

        if (verified) {
            installedPluginRegistry?.markInstalled(entry.pluginId, entry.version)
        }

        return verified
    }

    fun uninstall(pluginId: String): Boolean {
        if (pluginId.isBlank()) return false
        installedPluginRegistry?.markUninstalled(pluginId)
        return true
    }
}
