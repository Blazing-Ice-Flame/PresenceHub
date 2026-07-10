package com.ombryal.presencehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.ombryal.presencehub.plugins.InstalledPluginRegistry
import com.ombryal.presencehub.plugins.PluginInstallManager
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStore
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.service.ServiceController
import com.ombryal.presencehub.ui.theme.PresenceHubTheme

class MainActivity : ComponentActivity() {

    private lateinit var installedPluginRegistry: InstalledPluginRegistry
    private lateinit var pluginStore: PluginStore
    private lateinit var pluginInstallManager: PluginInstallManager

    private var storeState by mutableStateOf(PluginStoreState(isLoading = true))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installedPluginRegistry = InstalledPluginRegistry(this)
        pluginStore = PluginStore(installedPluginRegistry = installedPluginRegistry)
        pluginInstallManager = PluginInstallManager(
            installedPluginRegistry = installedPluginRegistry
        )

        refreshPluginStore()

        setContent {
            PresenceHubTheme {
                PresenceHubApp(
                    storeState = storeState,
                    onRefreshPlugins = { refreshPluginStore() },
                    onInstallPlugin = { plugin ->
                        pluginInstallManager.install(plugin)
                        refreshPluginStore()
                    },
                    onUninstallPlugin = { plugin ->
                        pluginInstallManager.uninstall(plugin.pluginId)
                        refreshPluginStore()
                    },
                    onStartRpc = { ServiceController.startRpcService(this) },
                    onStopRpc = { ServiceController.stopRpcService(this) }
                )
            }
        }
    }

    private fun refreshPluginStore() {
        storeState = storeState.copy(isLoading = true, errorMessage = null)

        val success = pluginStore.refresh()
        storeState = if (success) {
            PluginStoreState(
                plugins = pluginStore.getAvailablePlugins(),
                isLoading = false,
                errorMessage = null
            )
        } else {
            PluginStoreState(
                plugins = emptyList(),
                isLoading = false,
                errorMessage = "Failed to load plugin store."
            )
        }
    }
}
