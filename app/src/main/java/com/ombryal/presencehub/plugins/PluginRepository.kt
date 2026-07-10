package com.ombryal.presencehub.plugins

import com.ombryal.presencehub.utils.Constants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PluginRepository {

    fun fetchPluginIndex(): PluginIndex? {
        return try {
            val connection = openConnection(Constants.PLUGIN_INDEX_URL)
            val response = readResponse(connection)
            PluginJsonParser.parseIndex(response)
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

    private fun readResponse(connection: HttpURLConnection): String {
        return connection.inputStream.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.readText()
            }
        }
    }
}
