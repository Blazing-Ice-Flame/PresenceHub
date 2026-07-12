package com.ombryal.presencehub.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.ui.account.AccountScreen
import com.ombryal.presencehub.ui.account.AccountUiState
import com.ombryal.presencehub.ui.addapp.AddAppScreen
import com.ombryal.presencehub.ui.addapp.PluginDetailsScreen
import com.ombryal.presencehub.ui.home.HomeScreen
import com.ombryal.presencehub.ui.settings.SettingsScreen
import com.ombryal.presencehub.ui.settings.SettingsAccountsScreen
import com.ombryal.presencehub.ui.settings.SettingsThemeScreen
import com.ombryal.presencehub.ui.settings.SettingsAboutScreen
import com.ombryal.presencehub.ui.settings.SettingsUiState

object Routes {
    const val STORE = "store"
    const val HOME = "home"
    const val ACCOUNT = "account"
    const val SETTINGS = "settings"
    const val SETTINGS_ACCOUNTS = "settings_accounts"
    const val SETTINGS_THEME = "settings_theme"
    const val SETTINGS_ABOUT = "settings_about"
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

    val isSettingsScreen = currentRoute in listOf(
        Routes.SETTINGS, Routes.SETTINGS_ACCOUNTS, Routes.SETTINGS_THEME, Routes.SETTINGS_ABOUT
    )

    // Root Box to overlay the floating capsule
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        if (isSettingsScreen) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    },
                    title = {
                        Text(
                            text = when (currentRoute) {
                                Routes.HOME -> "PresenceHub"
                                Routes.STORE -> "Plugin Store"
                                Routes.ACCOUNT -> "Account"
                                Routes.SETTINGS -> "Settings"
                                Routes.SETTINGS_ACCOUNTS -> "Accounts"
                                Routes.SETTINGS_THEME -> "Theme"
                                Routes.SETTINGS_ABOUT -> "About"
                                Routes.PLUGIN_DETAILS -> "Plugin Details"
                                else -> "PresenceHub"
                            }
                        )
                    },
                    actions = {
                        if (!isSettingsScreen) {
                            IconButton(onClick = { navController.navigate(Routes.SETTINGS) }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    }
                )
            }
            // bottomBar removed entirely
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding(paddingValues) // only top insets now
            ) {
                composable(Routes.HOME) {
                    HomeScreen(
                        storeState = storeState,
                        onOpenStore = { navController.navigate(Routes.STORE) },
                        onOpenAccount = { navController.navigate(Routes.ACCOUNT) },
                        onOpenAbout = { navController.navigate(Routes.SETTINGS_ABOUT) },
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
                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        onAccountsClick = { navController.navigate(Routes.SETTINGS_ACCOUNTS) },
                        onThemeClick = { navController.navigate(Routes.SETTINGS_THEME) },
                        onAboutClick = { navController.navigate(Routes.SETTINGS_ABOUT) }
                    )
                }
                composable(Routes.SETTINGS_ACCOUNTS) {
                    SettingsAccountsScreen(accountState = accountState)
                }
                composable(Routes.SETTINGS_THEME) {
                    SettingsThemeScreen(
                        currentTheme = settingsState.themeMode,
                        onThemeSelected = { mode -> onUpdateSettings(settingsState.copy(themeMode = mode)) }
                    )
                }
                composable(Routes.SETTINGS_ABOUT) {
                    SettingsAboutScreen()
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

        // Floating glass capsule overlay (hidden on settings screens)
        if (!isSettingsScreen) {
            FloatingGlassCapsule(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp),   // raised above bottom edge
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
    }
}
