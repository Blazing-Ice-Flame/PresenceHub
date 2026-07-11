package com.ombryal.presencehub.plugins

class PluginStore(
    private val repository: PluginRepository = PluginRepository(),
    private val installedPluginRegistry: InstalledPluginRegistry? = null
) {
    private var remotePlugins: List<PluginRegistryEntry> = emptyList()

    fun refresh(): Boolean {
        val index = repository.fetchRemoteIndex()
        remotePlugins = index?.plugins?.map { remote ->
            val installedVersion = installedPluginRegistry?.getInstalledVersion(remote.pluginId)
            val installed = installedVersion != null
            val updateAvailable = installedVersion?.let {
                PluginVersionUtils.isNewerVersion(remote.version, it)
            } ?: false

            PluginRegistryEntry(
                pluginId = remote.pluginId,
                name = remote.name,
                version = remote.version,
                apiVersion = remote.apiVersion,
                author = remote.author,
                downloadUrl = remote.packageUrl,
                checksumSha256 = null,
                signature = null,
                description = remote.description,
                verified = remote.verified,
                installed = installed,
                installedVersion = installedVersion,
                updateAvailable = updateAvailable
            )
        } ?: emptyList()

        return index != null
    }

    fun getAvailablePlugins(): List<PluginRegistryEntry> {
        return remotePlugins
    }

    fun findById(pluginId: String): PluginRegistryEntry? {
        return remotePlugins.firstOrNull { it.pluginId == pluginId }
    }
}
