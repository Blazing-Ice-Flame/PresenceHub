package com.ombryal.presencehub.data.preferences

class SettingsRepository {
    private var activePluginId: String? = null
    private var updateIntervalMs: Long = 5000L

    fun setActivePluginId(pluginId: String?) {
        activePluginId = pluginId
    }

    fun getActivePluginId(): String? = activePluginId

    fun setUpdateIntervalMs(value: Long) {
        updateIntervalMs = value
    }

    fun getUpdateIntervalMs(): Long = updateIntervalMs
}
