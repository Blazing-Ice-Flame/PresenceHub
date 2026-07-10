package com.ombryal.presencehub.plugins

data class PluginDescriptor(
    val pluginId: String,
    val name: String,
    val version: String,
    val apiVersion: Int,
    val downloadUrl: String? = null,
    val checksumSha256: String? = null,
    val verified: Boolean = false
)
