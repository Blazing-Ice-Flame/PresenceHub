package com.ombryal.presencehub.plugins

data class PluginPackageIndexEntry(
    val pluginId: String,
    val version: String,
    val manifestUrl: String,
    val packageUrl: String,
    val checksumUrl: String? = null,
    val signatureUrl: String? = null,
    val verified: Boolean = false
)
