package com.ombryal.presencehub

import androidx.compose.runtime.Composable
import com.ombryal.presencehub.navigation.AppNavigation
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.account.AccountUiState
import com.ombryal.presencehub.ui.settings.SettingsUiState

@Composable
fun PresenceHubApp(
    storeState: PluginStoreState,
    accountState: AccountUiState,
    settingsState: SettingsUiState,
    onRefreshPlugins: () -> Unit,
    onInstallPlugin: (PluginRegistryEntry) -> Unit,
    onUninstallPlugin: (PluginRegistryEntry) -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit,
    onOpenSettings: () -> Unit,
    onUpdateSettings: (SettingsUiState) -> Unit
) {
    AppNavigation(
        storeState = storeState,
        accountState = accountState,
        settingsState = settingsState,
        onRefreshPlugins = onRefreshPlugins,
        onInstallPlugin = onInstallPlugin,
        onUninstallPlugin = onUninstallPlugin,
        onStartRpc = onStartRpc,
        onStopRpc = onStopRpc,
        onOpenSettings = onOpenSettings,
        onUpdateSettings = onUpdateSettings
    )
}
