package com.ombryal.presencehub.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.account.AccountScreen
import com.ombryal.presencehub.ui.about.AboutScreen
import com.ombryal.presencehub.ui.addapp.AddAppScreen
import com.ombryal.presencehub.ui.addapp.PluginDetailsScreen
import com.ombryal.presencehub.ui.home.HomeScreen
import com.ombryal.presencehub.ui.settings.SettingsScreen

object Routes {
    const val HOME = "home"
    const val STORE = "store"
    const val ACCOUNT = "account"
    const val ABOUT = "about"
    const val SETTINGS = "settings"
    const val PLUGIN_DETAILS = "plugin_details"
}

private data class BottomItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val bottomItems = listOf(
    BottomItem(Routes.HOME, "Home", Icons.Default.Home),
    BottomItem(Routes.ACCOUNT, "Account", Icons.Default.Person),
    BottomItem(Routes.ABOUT, "About", Icons.Default.Info)
)

@OptIn(ExperimentalMaterial3Api::class)
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Routes.HOME
    var selectedPlugin by remember { mutableStateOf<PluginRegistryEntry?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (currentRoute) {
                            Routes.HOME -> "PresenceHub"
                            Routes.STORE -> "Plugin Store"
                            Routes.ACCOUNT -> "Account"
                            Routes.ABOUT -> "About"
                            Routes.SETTINGS -> "Settings"
                            Routes.PLUGIN_DETAILS -> "Plugin Details"
                            else -> "PresenceHub"
                        }
                    )
                },
                actions = {
                    if (currentRoute == Routes.HOME) {
                        IconButton(onClick = { navController.navigate(Routes.STORE) }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Open Plugin Store"
                            )
                        }
                    }

                    IconButton(onClick = { navController.navigate(Routes.SETTINGS) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(item.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    storeState = storeState,
                    onOpenStore = { navController.navigate(Routes.STORE) },
                    onOpenAccount = { navController.navigate(Routes.ACCOUNT) },
                    onOpenAbout = { navController.navigate(Routes.ABOUT) },
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) }
                )
            }

            composable(Routes.STORE) {
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
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.ACCOUNT) {
                AccountScreen(
                    onStartRpc = onStartRpc,
                    onStopRpc = onStopRpc
                )
            }

            composable(Routes.ABOUT) {
                AboutScreen()
            }

            composable(Routes.SETTINGS) {
                SettingsScreen(
                    onStartRpc = onStartRpc,
                    onStopRpc = onStopRpc,
                    onRefreshPlugins = onRefreshPlugins
                )
            }

            composable(Routes.PLUGIN_DETAILS) {
                selectedPlugin?.let { plugin ->
                    PluginDetailsScreen(
                        plugin = plugin,
                        onInstall = onInstallPlugin,
                        onUninstall = onUninstallPlugin,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
