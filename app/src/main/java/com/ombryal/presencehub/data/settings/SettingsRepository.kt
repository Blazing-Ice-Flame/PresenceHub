package com.ombryal.presencehub.data.settings

import android.content.Context
import com.ombryal.presencehub.ui.settings.SettingsUiState

class SettingsRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): SettingsUiState {
        return SettingsUiState(
            autoStartRpc = prefs.getBoolean(KEY_AUTO_START_RPC, false),
            dynamicColors = prefs.getBoolean(KEY_DYNAMIC_COLORS, true),
            showPluginUpdates = prefs.getBoolean(KEY_SHOW_PLUGIN_UPDATES, true),
            showVideoTitle = prefs.getBoolean(KEY_SHOW_VIDEO_TITLE, true),
            showChannelName = prefs.getBoolean(KEY_SHOW_CHANNEL_NAME, true),
            showProgress = prefs.getBoolean(KEY_SHOW_PROGRESS, true),
            showElapsedTime = prefs.getBoolean(KEY_SHOW_ELAPSED_TIME, true),
            notificationAccess = prefs.getBoolean(KEY_NOTIFICATION_ACCESS, false),
            backgroundUpdates = prefs.getBoolean(KEY_BACKGROUND_UPDATES, true),
            debugLogging = prefs.getBoolean(KEY_DEBUG_LOGGING, false),
            developerMode = prefs.getBoolean(KEY_DEVELOPER_MODE, false),
            hideTitles = prefs.getBoolean(KEY_HIDE_TITLES, false),
            hideChannels = prefs.getBoolean(KEY_HIDE_CHANNELS, false),
            clearHistoryOnExit = prefs.getBoolean(KEY_CLEAR_HISTORY_ON_EXIT, false)
        )
    }

    fun save(state: SettingsUiState) {
        prefs.edit()
            .putBoolean(KEY_AUTO_START_RPC, state.autoStartRpc)
            .putBoolean(KEY_DYNAMIC_COLORS, state.dynamicColors)
            .putBoolean(KEY_SHOW_PLUGIN_UPDATES, state.showPluginUpdates)
            .putBoolean(KEY_SHOW_VIDEO_TITLE, state.showVideoTitle)
            .putBoolean(KEY_SHOW_CHANNEL_NAME, state.showChannelName)
            .putBoolean(KEY_SHOW_PROGRESS, state.showProgress)
            .putBoolean(KEY_SHOW_ELAPSED_TIME, state.showElapsedTime)
            .putBoolean(KEY_NOTIFICATION_ACCESS, state.notificationAccess)
            .putBoolean(KEY_BACKGROUND_UPDATES, state.backgroundUpdates)
            .putBoolean(KEY_DEBUG_LOGGING, state.debugLogging)
            .putBoolean(KEY_DEVELOPER_MODE, state.developerMode)
            .putBoolean(KEY_HIDE_TITLES, state.hideTitles)
            .putBoolean(KEY_HIDE_CHANNELS, state.hideChannels)
            .putBoolean(KEY_CLEAR_HISTORY_ON_EXIT, state.clearHistoryOnExit)
            .apply()
    }

    private companion object {
        const val PREFS_NAME = "presencehub_settings"

        const val KEY_AUTO_START_RPC = "auto_start_rpc"
        const val KEY_DYNAMIC_COLORS = "dynamic_colors"
        const val KEY_SHOW_PLUGIN_UPDATES = "show_plugin_updates"
        const val KEY_SHOW_VIDEO_TITLE = "show_video_title"
        const val KEY_SHOW_CHANNEL_NAME = "show_channel_name"
        const val KEY_SHOW_PROGRESS = "show_progress"
        const val KEY_SHOW_ELAPSED_TIME = "show_elapsed_time"
        const val KEY_NOTIFICATION_ACCESS = "notification_access"
        const val KEY_BACKGROUND_UPDATES = "background_updates"
        const val KEY_DEBUG_LOGGING = "debug_logging"
        const val KEY_DEVELOPER_MODE = "developer_mode"
        const val KEY_HIDE_TITLES = "hide_titles"
        const val KEY_HIDE_CHANNELS = "hide_channels"
        const val KEY_CLEAR_HISTORY_ON_EXIT = "clear_history_on_exit"
    }
}
