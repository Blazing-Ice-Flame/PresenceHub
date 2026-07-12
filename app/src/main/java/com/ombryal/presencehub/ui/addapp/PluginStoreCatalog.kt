package com.ombryal.presencehub.ui.addapp

data class PluginStoreCatalogItem(
    val pluginId: String,
    val title: String,
    val subtitle: String,
    val availableInV1: Boolean
)

object PluginStoreCatalog {
    val items = listOf(
        PluginStoreCatalogItem(
            pluginId = "youtube",
            title = "YouTube",
            subtitle = "Show video title, channel, progress, and elapsed time.",
            availableInV1 = true
        ),
        PluginStoreCatalogItem(
            pluginId = "youtube_music",
            title = "YouTube Music",
            subtitle = "Music status with album and track details.",
            availableInV1 = false
        ),
        PluginStoreCatalogItem(
            pluginId = "spotify",
            title = "Spotify",
            subtitle = "Listening status, album art, and track progress.",
            availableInV1 = false
        ),
        PluginStoreCatalogItem(
            pluginId = "vlc",
            title = "VLC",
            subtitle = "Local video playback presence.",
            availableInV1 = false
        ),
        PluginStoreCatalogItem(
            pluginId = "jellyfin",
            title = "Jellyfin",
            subtitle = "Streaming and media library presence.",
            availableInV1 = false
        ),
        PluginStoreCatalogItem(
            pluginId = "plex",
            title = "Plex",
            subtitle = "Media playback and streaming presence.",
            availableInV1 = false
        )
    )
}
