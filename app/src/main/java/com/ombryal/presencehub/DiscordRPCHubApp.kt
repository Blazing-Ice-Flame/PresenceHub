package com.ombryal.presencehub

import android.app.Application
import com.ombryal.presencehub.plugins.InstalledPluginRegistry
import com.ombryal.presencehub.plugins.PluginManager
import com.ombryal.presencehub.providers.youtube.YouTubeProvider
import com.ombryal.presencehub.rpc.RPCManager

class DiscordRPCHubApp : Application() {

    lateinit var installedPluginRegistry: InstalledPluginRegistry
        private set

    lateinit var pluginManager: PluginManager
        private set

    lateinit var rpcManager: RPCManager
        private set

    override fun onCreate() {
        super.onCreate()

        installedPluginRegistry = InstalledPluginRegistry(this)
        pluginManager = PluginManager(installedPluginRegistry)
        pluginManager.registerPlugin(YouTubeProvider())
        pluginManager.startActivePlugin()

        rpcManager = RPCManager(pluginManager)
    }
}
