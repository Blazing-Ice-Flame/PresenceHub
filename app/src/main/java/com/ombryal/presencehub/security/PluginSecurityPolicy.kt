package com.ombryal.presencehub.security

import com.ombryal.presencehub.plugins.PluginRegistryEntry

object PluginSecurityPolicy {

    const val CURRENT_API_VERSION = 1

    fun isApiCompatible(entry: PluginRegistryEntry): Boolean {
        return entry.apiVersion == CURRENT_API_VERSION
    }

    fun isDownloadUrlValid(entry: PluginRegistryEntry): Boolean {
        return entry.downloadUrl.isNotBlank()
    }

    fun isPluginEligible(entry: PluginRegistryEntry): Boolean {
        return isApiCompatible(entry) && isDownloadUrlValid(entry)
    }
}
