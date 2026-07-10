package com.ombryal.presencehub.plugins

data class PluginStoreState(
    val plugins: List<PluginRegistryEntry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
