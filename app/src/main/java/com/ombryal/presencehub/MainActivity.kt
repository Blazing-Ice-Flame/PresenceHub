package com.ombryal.presencehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.service.ServiceController
import com.ombryal.presencehub.ui.AppViewModel
import com.ombryal.presencehub.ui.theme.PresenceHubTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PresenceHubTheme {
                val storeState = viewModel.storeState.collectAsStateWithLifecycle().value

                PresenceHubApp(
                    storeState = storeState,
                    onRefreshPlugins = { viewModel.refreshPluginStore() },
                    onInstallPlugin = { plugin: PluginRegistryEntry -> viewModel.installPlugin(plugin) },
                    onUninstallPlugin = { plugin: PluginRegistryEntry -> viewModel.uninstallPlugin(plugin) },
                    onStartRpc = { ServiceController.startRpcService(this) },
                    onStopRpc = { ServiceController.stopRpcService(this) }
                )
            }
        }
    }
}
