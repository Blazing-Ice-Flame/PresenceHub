package com.ombryal.presencehub.ui.account

data class PermissionItem(
    val label: String,
    val granted: Boolean
)

data class AccountUiState(
    val connected: Boolean = false,
    val displayName: String = "VoidDev",
    val handle: String = "voiddev_#1337",
    val statusMessage: String = "Discord is connected and Rich Presence is active.",
    val activeProvider: String = "YouTube",
    val activeTitle: String = "How to Build a Discord Bot",
    val activeTime: String = "00:04 / 12:38",
    val liveStatus: String = "Active",
    val permissions: List<PermissionItem> = listOf(
        PermissionItem("Access your username, avatar, and server information", true),
        PermissionItem("Update your Rich Presence", true),
        PermissionItem("Access images for thumbnails and large/small images", true),
        PermissionItem("Open links (YouTube, Channels, etc.)", true)
    )
)
