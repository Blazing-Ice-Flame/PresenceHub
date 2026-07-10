package com.ombryal.presencehub.plugins

import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class PluginDownloadManager {

    fun downloadBytes(downloadUrl: String): ByteArray? {
        return try {
            val connection = openConnection(downloadUrl)
            connection.inputStream.use { input ->
                readAllBytes(input)
            }
        } catch (_: Throwable) {
            null
        }
    }

    private fun openConnection(rawUrl: String): HttpURLConnection {
        val url = URL(rawUrl)
        return (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 10_000
            readTimeout = 10_000
            useCaches = false
        }
    }

    private fun readAllBytes(input: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        val buffer = ByteArray(8_192)

        while (true) {
            val read = input.read(buffer)
            if (read <= 0) break
            output.write(buffer, 0, read)
        }

        return output.toByteArray()
    }
}
