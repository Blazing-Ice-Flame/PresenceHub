package com.ombryal.presencehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.about.AboutScreen
import com.ombryal.presencehub.ui.account.AccountScreen
import com.ombryal.presencehub.ui.addapp.AddAppScreen
import com.ombryal.presencehub.ui.addapp.PluginDetailsScreen
import com.ombryal.presencehub.ui.home.HomeScreen
import com.ombryal.presencehub.ui.settings.SettingsScreen

object Routes {
    const val HOME = "home"
    const val ACCOUNT = "account"
    const val ABOUT = "about"
    const val SETTINGS = "settings"
    const val ADD_APP = "add_app"
    const val PLUGIN_DETAILS = "plugin_details"
}

@Composable
fun AppNavigation(
    storeState: PluginStoreState,
    onRefreshPlugins: () -> Unit,
    onInstallPlugin: (PluginRegistryEntry) -> Unit,
    onUninstallPlugin: (PluginRegistryEntry) -> Unit,
    onStartRpc: () -> Unit,
    onStopRpc: () -> Unit
) {
    val navController = rememberNavController()
    var selectedPlugin by remember { mutableStateOf<PluginRegistryEntry?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                pluginCount = storeState.plugins.size,
                onOpenAccount = { navController.navigate(Routes.ACCOUNT) },
                onOpenAbout = { navController.navigate(Routes.ABOUT) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                onOpenAddApp = { navController.navigate(Routes.ADD_APP) }
            )
        }

        composable(Routes.ACCOUNT) {
            AccountScreen(
                onBack = { navController.popBackStack() },
                onStartRpc = onStartRpc,
                onStopRpc = onStopRpc
            )
        }

        composable(Routes.ABOUT) {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onStartRpc = onStartRpc,
                onStopRpc = onStopRpc,
                onRefreshPlugins = onRefreshPlugins
            )
        }

        composable(Routes.ADD_APP) {
            AddAppScreen(
                availablePlugins = storeState.plugins,
                isLoading = storeState.isLoading,
                errorMessage = storeState.errorMessage,
                onRefresh = onRefreshPlugins,
                onInstall = onInstallPlugin,
                onOpenDetails = { plugin ->
                    selectedPlugin = plugin
                    navController.navigate(Routes.PLUGIN_DETAILS)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PLUGIN_DETAILS) {
            val plugin = selectedPlugin
            if (plugin != null) {
                PluginDetailsScreen(
                    plugin = plugin,
                    onInstall = onInstallPlugin,
                    onUninstall = onUninstallPlugin,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
