package com.ombryal.presencehub.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
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
import com.ombryal.presencehub.ui.settings.SettingsAccountsScreen
import com.ombryal.presencehub.ui.settings.SettingsThemeScreen
import com.ombryal.presencehub.ui.settings.SettingsUiState

object Routes {
    const val HOME = "home"
    const val STORE = "store"
    const val ACCOUNT = "account"
    const val ABOUT = "about"
    const val SETTINGS = "settings"
    const val SETTINGS_ACCOUNTS = "settings_accounts"
    const val SETTINGS_THEME = "settings_theme"
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
                            Routes.SETTINGS_ACCOUNTS -> "Accounts"
                            Routes.SETTINGS_THEME -> "Theme"
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
            FloatingGlassCapsule(
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
                    onAccountsClick = { navController.navigate(Routes.SETTINGS_ACCOUNTS) },
                    onThemeClick = { navController.navigate(Routes.SETTINGS_THEME) }
                )
            }
            composable(Routes.SETTINGS_ACCOUNTS) {
                SettingsAccountsScreen(
                    accountState = accountState
                )
            }
            composable(Routes.SETTINGS_THEME) {
                SettingsThemeScreen(
                    currentTheme = settingsState.themeMode,
                    onThemeSelected = { mode -> onUpdateSettings(settingsState.copy(themeMode = mode)) }
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
private fun FloatingGlassCapsule(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        TabItem(Routes.ACCOUNT, "Account", Icons.Default.Person),
        TabItem(Routes.HOME, "Home", Icons.Default.Home),
        TabItem(Routes.ABOUT, "About", Icons.Default.Info)
    )

    val activeIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)
    val density = LocalDensity.current

    var totalWidth by remember { mutableStateOf(0.dp) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .padding(bottom = 28.dp)
            .height(80.dp)
            .onGloballyPositioned { coordinates ->
                totalWidth = with(density) { coordinates.size.width.toDp() }
            }
    ) {
        val capsuleWidth = totalWidth
        val pillWidth = capsuleWidth * 0.32f
        val segmentWidth = capsuleWidth / 3
        val pillOffsetX by animateDpAsState(
            targetValue = segmentWidth * activeIndex + (segmentWidth - pillWidth) / 2,
            animationSpec = tween(durationMillis = 350)
        )

        // Outer capsule container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .shadow(24.dp, RoundedCornerShape(36.dp), ambientColor = Color.Magenta.copy(alpha = 0.15f), spotColor = Color.Magenta.copy(alpha = 0.15f))
                .clip(RoundedCornerShape(36.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC1A1A2E),
                            Color(0xCC10101A)
                        )
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(36.dp))
        ) {
            // Animated pill background
            Box(
                modifier = Modifier
                    .offset(x = pillOffsetX)
                    .padding(vertical = 6.dp)
                    .size(width = pillWidth, height = 68.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0x558E96FF),
                                Color(0x336366F1)
                            )
                        ),
                        RoundedCornerShape(30.dp)
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(30.dp))
            )

            // Tabs row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val selected = index == activeIndex
                    Tab(
                        item = item,
                        selected = selected,
                        onClick = { onNavigate(item.route) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Tab(
    item: TabItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .scale(if (selected) 1.05f else 1f)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier.size(if (item.route == Routes.HOME) 26.dp else 22.dp),
            tint = if (selected) Color.White else Color(0x99B7B9C8)
        )
        Text(
            text = item.label,
            fontSize = if (item.route == Routes.HOME) 11.sp else 10.sp,
            color = if (selected) Color.White else Color(0x99B7B9C8)
        )
    }
}

private data class TabItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
