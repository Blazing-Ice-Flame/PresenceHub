package com.ombryal.presencehub.plugins

object PluginDescriptorMapper {

    fun fromRegistryEntry(entry: PluginRegistryEntry): PluginDescriptor {
        return PluginDescriptor(
            pluginId = entry.pluginId,
            name = entry.name,
            version = entry.version,
            apiVersion = entry.apiVersion,
            author = entry.author,
            description = entry.description,
            verified = entry.verified,
            installed = entry.installed,
            installedVersion = entry.installedVersion,
            updateAvailable = entry.updateAvailable
        )
    }

    fun toRegistryEntry(
        descriptor: PluginDescriptor,
        downloadUrl: String
    ): PluginRegistryEntry {
        return PluginRegistryEntry(
            pluginId = descriptor.pluginId,
            name = descriptor.name,
            version = descriptor.version,
            apiVersion = descriptor.apiVersion,
            author = descriptor.author,
            downloadUrl = downloadUrl,
            checksumSha256 = null,
            signature = null,
            description = descriptor.description,
            verified = descriptor.verified,
            installed = descriptor.installed,
            installedVersion = descriptor.installedVersion,
            updateAvailable = descriptor.updateAvailable
        )
    }
}
