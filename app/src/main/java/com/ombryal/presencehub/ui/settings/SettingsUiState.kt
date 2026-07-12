package com.ombryal.presencehub.ui.settings

enum class ThemeMode { SYSTEM, DARK, LIGHT, GLASS }

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
    val clearHistoryOnExit: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)
