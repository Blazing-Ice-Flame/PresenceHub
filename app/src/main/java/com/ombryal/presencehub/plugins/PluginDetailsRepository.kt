package com.ombryal.presencehub.plugins

class PluginDetailsRepository(
    private val remoteManifestRepository: RemotePluginManifestRepository = RemotePluginManifestRepository()
) {

    fun fetchDetails(plugin: PluginRegistryEntry): PluginPackageManifest? {
        val manifestUrl = plugin.downloadUrl.replace("plugin.apk", "manifest.json")
        return if (manifestUrl.isNotBlank()) {
            remoteManifestRepository.fetchManifest(manifestUrl)
        } else {
            null
        }
    }
}
