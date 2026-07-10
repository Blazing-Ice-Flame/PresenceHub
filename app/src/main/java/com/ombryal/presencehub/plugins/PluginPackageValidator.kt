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
}
