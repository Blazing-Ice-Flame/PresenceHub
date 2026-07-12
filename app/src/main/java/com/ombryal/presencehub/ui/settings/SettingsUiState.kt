package com.ombryal.presencehub.ui.settings

data class SettingsUiState(
    val autoStartRpc: Boolean = false,
    val dynamicColors: Boolean = true,
    val showPluginUpdates: Boolean = true,
    val showVideoTitle: Boolean = true,
    val showChannelName: Boolean = true,
    val showProgress: Boolean = true,
    val showElapsedTime: Boolean = true,
    val notificationAccess: Boolean = false,
    val backgroundUpdates: Boolean = true,
    val debugLogging: Boolean = false,
    val developerMode: Boolean = false,
    val hideTitles: Boolean = false,
    val hideChannels: Boolean = false,
    val clearHistoryOnExit: Boolean = false
)
