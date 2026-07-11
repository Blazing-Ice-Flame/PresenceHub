package com.ombryal.presencehub.plugins

import org.json.JSONArray
import org.json.JSONObject

object PluginJsonParser {

    fun parseIndex(rawJson: String): PluginIndex? {
        return try {
            val root = JSONObject(rawJson)
            val version = root.optInt("version", 1)
            val pluginsArray = root.optJSONArray("plugins") ?: JSONArray()

            val plugins = buildList {
                for (i in 0 until pluginsArray.length()) {
                    val item = pluginsArray.optJSONObject(i) ?: continue
                    add(parseEntry(item))
                }
            }

            PluginIndex(
                version = version,
                plugins = plugins
            )
        } catch (_: Throwable) {
            null
        }
    }

    private fun parseEntry(obj: JSONObject): PluginRegistryEntry {
        return PluginRegistryEntry(
            pluginId = obj.optString("pluginId"),
            name = obj.optString("name"),
            version = obj.optString("version"),
            apiVersion = obj.optInt("apiVersion", 1),
            author = obj.optString("author", "Unknown"),
            downloadUrl = obj.optString("packageUrl").ifBlank { obj.optString("downloadUrl") },
            checksumSha256 = obj.optString("checksumSha256").takeIf { it.isNotBlank() },
            signature = obj.optString("signature").takeIf { it.isNotBlank() },
            description = obj.optString("description").takeIf { it.isNotBlank() },
            verified = obj.optBoolean("verified", false)
        )
    }
}
