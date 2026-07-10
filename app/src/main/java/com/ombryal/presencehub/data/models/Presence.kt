package com.ombryal.presencehub.data.models

data class Presence(
    val pluginId: String,
    val providerName: String,
    val title: String,
    val subtitle: String? = null,
    val state: String? = null,
    val largeImageKey: String? = null,
    val smallImageKey: String? = null,
    val startTimestamp: Long? = null,
    val endTimestamp: Long? = null,
    val buttonLabel: String? = null,
    val buttonUrl: String? = null
)
