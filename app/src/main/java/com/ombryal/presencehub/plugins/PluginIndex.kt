package com.ombryal.presencehub.plugins

data class PluginIndex(
    val version: Int,
    val plugins: List<PluginRegistryEntry>
)
