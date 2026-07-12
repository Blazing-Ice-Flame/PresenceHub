package com.ombryal.presencehub.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
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
        },
        bottomBar = {
            if (!isSettingsScreen) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
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
}

@Composable
private fun FloatingGlassCapsule(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        TabItem(Routes.STORE, "Store", Icons.Default.Store),
        TabItem(Routes.HOME, "Home", Icons.Default.Home),
        TabItem(Routes.ACCOUNT, "Account", Icons.Default.Person)
    )

    val activeIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)
    val density = LocalDensity.current

    var capsuleWidthDp by remember { mutableStateOf(0.dp) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(0.88f)
            .padding(bottom = 20.dp)
            .height(64.dp)
            .onGloballyPositioned { coordinates ->
                capsuleWidthDp = with(density) { coordinates.size.width.toDp() }
            }
    ) {
        val pillWidth = capsuleWidthDp * 0.28f   // compact pill, same for all
        val segmentWidth = capsuleWidthDp / 3
        val pillOffsetX by animateDpAsState(
            targetValue = segmentWidth * activeIndex + (segmentWidth - pillWidth) / 2,
            animationSpec = tween(durationMillis = 350)
        )

        // Outer glass container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Magenta.copy(alpha = 0.1f),
                    spotColor = Color.Magenta.copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC1A1A2E),
                            Color(0xCC10101A)
                        )
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(28.dp))
        ) {
            // Animated pill highlight (small, same size for all tabs)
            Box(
                modifier = Modifier
                    .offset(x = pillOffsetX)
                    .padding(vertical = 6.dp)
                    .size(width = pillWidth, height = 52.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0x558E96FF),
                                Color(0x336366F1)
                            )
                        ),
                        RoundedCornerShape(22.dp)
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(22.dp))
            )

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
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
    val labelAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(250)
    )
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        animationSpec = tween(300)
    )

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .scale(scale)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier.size(22.dp),
            tint = if (selected) Color.White else Color(0x99B7B9C8)
        )
        if (selected) {
            Text(
                text = item.label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.alpha(labelAlpha)
            )
        } else {
            // Keep a tiny space so height stays consistent
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

private data class TabItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
