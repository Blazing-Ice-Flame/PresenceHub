package com.ombryal.presencehub

import android.app.Application
import com.ombryal.presencehub.plugins.PluginManager
import com.ombryal.presencehub.providers.youtube.YouTubeProvider
import com.ombryal.presencehub.rpc.RPCManager

class DiscordRPCHubApp : Application() {

    lateinit var pluginManager: PluginManager
        private set

    lateinit var rpcManager: RPCManager
        private set

    override fun onCreate() {
        super.onCreate()

        pluginManager = PluginManager()
        pluginManager.registerPlugin(YouTubeProvider())
        pluginManager.startActivePlugin()

        rpcManager = RPCManager(pluginManager)
    }
}
