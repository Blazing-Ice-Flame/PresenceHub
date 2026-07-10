package com.ombryal.presencehub.plugins

data class PluginPackageManifest(
    val pluginId: String,
    val name: String,
    val version: String,
    val apiVersion: Int,
    val author: String,
    val description: String? = null,
    val packageUrl: String,
    val checksumSha256: String? = null,
    val signatureUrl: String? = null,
    val verified: Boolean = false
)
