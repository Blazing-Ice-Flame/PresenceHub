package com.ombryal.presencehub.plugins

data class RemotePluginIndex(
    val version: Int,
    val plugins: List<RemotePluginPackage>
)
