package com.ombryal.presencehub.plugins

import android.content.Context
import org.json.JSONObject

class InstalledPluginRegistry(context: Context) {

    private val preferences = context.getSharedPreferences(
        "installed_plugins",
        Context.MODE_PRIVATE
    )

    fun getInstalledPlugins(): Map<String, String> {
        val raw = preferences.getString(KEY_INSTALLED_PLUGINS_JSON, null) ?: return emptyMap()

        return try {
            val root = JSONObject(raw)
            buildMap {
                root.keys().forEach { key ->
                    put(key, root.optString(key))
                }
            }
        } catch (_: Throwable) {
            emptyMap()
        }
    }

    fun getInstalledVersion(pluginId: String): String? {
        return getInstalledPlugins()[pluginId]
    }

    fun isInstalled(pluginId: String): Boolean {
        return getInstalledVersion(pluginId) != null
    }

    fun markInstalled(pluginId: String, version: String) {
        val updated = getInstalledPlugins().toMutableMap()
        updated[pluginId] = version

        val json = JSONObject(updated as Map<*, *>).toString()
        preferences.edit().putString(KEY_INSTALLED_PLUGINS_JSON, json).apply()
    }

    fun markUninstalled(pluginId: String) {
        val updated = getInstalledPlugins().toMutableMap()
        updated.remove(pluginId)

        val json = JSONObject(updated as Map<*, *>).toString()
        preferences.edit().putString(KEY_INSTALLED_PLUGINS_JSON, json).apply()
    }

    fun clearAll() {
        preferences.edit().remove(KEY_INSTALLED_PLUGINS_JSON).apply()
    }

    private companion object {
        const val KEY_INSTALLED_PLUGINS_JSON = "key_installed_plugins_json"
    }
}
