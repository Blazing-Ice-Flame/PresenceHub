package com.ombryal.presencehub.plugins

object PluginVersionUtils {

    fun isNewerVersion(remoteVersion: String, localVersion: String?): Boolean {
        if (localVersion.isNullOrBlank()) return false
        if (remoteVersion.isBlank()) return false

        val remoteParts = remoteVersion.split(".").mapNotNull { it.toIntOrNull() }
        val localParts = localVersion.split(".").mapNotNull { it.toIntOrNull() }

        val maxLength = maxOf(remoteParts.size, localParts.size)

        for (i in 0 until maxLength) {
            val remote = remoteParts.getOrElse(i) { 0 }
            val local = localParts.getOrElse(i) { 0 }

            if (remote > local) return true
            if (remote < local) return false
        }

        return false
    }
}
