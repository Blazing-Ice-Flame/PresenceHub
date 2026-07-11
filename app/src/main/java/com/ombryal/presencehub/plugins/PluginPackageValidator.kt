package com.ombryal.presencehub.plugins

object PluginPackageValidator {

    fun isCompatible(manifest: PluginPackageManifest): Boolean {
        return manifest.pluginId.isNotBlank() &&
                manifest.name.isNotBlank() &&
                manifest.version.isNotBlank() &&
                manifest.apiVersion > 0 &&
                manifest.author.isNotBlank() &&
                manifest.packageUrl.isNotBlank()
    }

    fun isRemotePackageValid(remote: RemotePluginPackage): Boolean {
        return remote.pluginId.isNotBlank() &&
                remote.name.isNotBlank() &&
                remote.version.isNotBlank() &&
                remote.apiVersion > 0 &&
                remote.author.isNotBlank() &&
                remote.manifestUrl.isNotBlank() &&
                remote.packageUrl.isNotBlank()
    }
}
