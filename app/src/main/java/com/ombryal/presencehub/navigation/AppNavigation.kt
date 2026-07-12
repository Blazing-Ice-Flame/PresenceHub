package com.ombryal.presencehub.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.account.AccountScreen
import com.ombryal.presencehub.ui.account.AccountUiState
import com.ombryal.presencehub.ui.about.AboutScreen
import com.ombryal.presencehub.ui.addapp.AddAppScreen
import com.ombryal.presencehub.ui.addapp.PluginDetailsScreen
import com.ombryal.presencehub.ui.home.HomeScreen
import com.ombryal.presencehub.ui.settings.SettingsScreen
import com.ombryal.presencehub.ui.settings.SettingsUiState

object Routes {
    const val HOME = "home"
    const val STORE = "store"
    const val ACCOUNT = "account"
    const val ABOUT = "about"
    const val SETTINGS = "settings"
    const val PLUGIN_DETAILS = "plugin_details"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
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
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Routes.HOME
    var selectedPlugin by remember { mutableStateOf<PluginRegistryEntry?>(null) }

    Scaffold(
        containerColor = Color.Transparent,
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
            FloatingGlassBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
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
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.ACCOUNT) {
                AccountScreen(
                    state = accountState,
                    onStartRpc = onStartRpc,
                    onStopRpc = onStopRpc
                )
            }
            composable(Routes.ABOUT) {
                AboutScreen()
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    state = settingsState,
                    accountState = accountState,
                    onUpdate = onUpdateSettings,
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
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingGlassBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Triple(Routes.HOME, "Home", Icons.Default.Home),
        Triple(Routes.ABOUT, "About", Icons.Default.Info),
        Triple(Routes.ACCOUNT, "Account", Icons.Default.Person)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC1A1A2E),
                            Color(0xCC10101A)
                        )
                    )
                )
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { (route, label, icon) ->
                val selected = currentRoute == route
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (selected) {
                                    Modifier
                                        .background(
                                            Brush.horizontalGradient(
                                                colors = listOf(
                                                    Color(0x668E96FF),
                                                    Color(0x336366F1)
                                                )
                                            ),
                                            RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 14.dp, vertical = 4.dp)
                                } else {
                                    Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                                }
                            )
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            modifier = Modifier.size(
                                if (route == Routes.HOME) 28.dp else 24.dp
                            ),
                            tint = if (selected) Color(0xFFE0E7FF) else Color(0x99B7B9C8)
                        )
                        Text(
                            text = label,
                            fontSize = if (route == Routes.HOME) 12.sp else 11.sp,
                            color = if (selected) Color(0xFFE0E7FF) else Color(0x99B7B9C8)
                        )
                    }
                }
            }
        }
    }
}
