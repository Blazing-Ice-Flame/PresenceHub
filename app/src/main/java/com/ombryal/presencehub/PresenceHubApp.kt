package com.ombryal.presencehub

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.ombryal.presencehub.navigation.AppNavigation
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.account.AccountUiState

@Composable
fun PresenceHubApp(
    storeState: PluginStoreState,
    accountState: AccountUiState,
    onRefreshPlugins: () -> Unit,
    onInstallPlugin: (PluginRegistryEntry) -> Unit,
    onUninstallPlugin: (PluginRegistryEntry) -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 0.92f
                    scaleY = 0.92f
                    transformOrigin = TransformOrigin(0.5f, 0f)
                }
        ) {
            AppNavigation(
                storeState = storeState,
                accountState = accountState,
                onRefreshPlugins = onRefreshPlugins,
                onInstallPlugin = onInstallPlugin,
                onUninstallPlugin = onUninstallPlugin,
                onStartRpc = onStartRpc,
                onStopRpc = onStopRpc
            )
        }
    }
}
