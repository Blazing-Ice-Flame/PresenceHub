package com.ombryal.presencehub.plugins

class PluginArtifactResolver(
    private val baseRepoUrl: String
) {

    fun manifestUrl(pluginId: String, version: String): String {
        return "$baseRepoUrl/plugins/$pluginId/$version/${PluginPackagePaths.MANIFEST_FILE}"
    }

    fun packageUrl(pluginId: String, version: String): String {
        return "$baseRepoUrl/plugins/$pluginId/$version/${PluginPackagePaths.PACKAGE_FILE}"
    }

    fun checksumUrl(pluginId: String, version: String): String {
        return "$baseRepoUrl/plugins/$pluginId/$version/${PluginPackagePaths.CHECKSUM_FILE}"
    }

    fun signatureUrl(pluginId: String, version: String): String {
        return "$baseRepoUrl/plugins/$pluginId/$version/${PluginPackagePaths.SIGNATURE_FILE}"
    }
}
