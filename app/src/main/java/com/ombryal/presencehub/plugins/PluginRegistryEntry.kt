package com.ombryal.presencehub.plugins

data class PluginRegistryEntry(
    val pluginId: String,
    val name: String,
    val version: String,
    val apiVersion: Int,
    val downloadUrl: String,
    val checksumSha256: String? = null,
    val signature: String? = null,
    val description: String? = null,
    val verified: Boolean = false,
    val installed: Boolean = false,
    val installedVersion: String? = null,
    val updateAvailable: Boolean = false
)
