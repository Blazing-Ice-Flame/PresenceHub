package com.ombryal.presencehub.plugins

class PluginStore(
    private val repository: PluginRepository = PluginRepository(),
    private val installedPluginRegistry: InstalledPluginRegistry? = null
) {
    private var remotePlugins: List<PluginRegistryEntry> = emptyList()

    fun refresh(): Boolean {
        val index = repository.fetchPluginIndex()
        remotePlugins = index?.plugins?.map { plugin ->
            val installedVersion = installedPluginRegistry?.getInstalledVersion(plugin.pluginId)
            val installed = installedVersion != null
            val updateAvailable = installedVersion?.let {
                PluginVersionUtils.isNewerVersion(plugin.version, it)
            } ?: false

            plugin.copy(
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
