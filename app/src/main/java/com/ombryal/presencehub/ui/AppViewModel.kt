package com.ombryal.presencehub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ombryal.presencehub.DiscordRPCHubApp
import com.ombryal.presencehub.plugins.InstalledPluginRegistry
import com.ombryal.presencehub.plugins.PluginInstallManager
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStore
import com.ombryal.presencehub.plugins.PluginStoreState
import com.ombryal.presencehub.rpc.Presence
import com.ombryal.presencehub.ui.account.AccountUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val installedPluginRegistry = InstalledPluginRegistry(application)
    private val pluginStore = PluginStore(installedPluginRegistry = installedPluginRegistry)
    private val pluginInstallManager = PluginInstallManager(
        installedPluginRegistry = installedPluginRegistry
    )

    private val app get() = getApplication<DiscordRPCHubApp>()

    private val _storeState = MutableStateFlow(PluginStoreState(isLoading = true))
    val storeState: StateFlow<PluginStoreState> = _storeState.asStateFlow()

    private val _accountState = MutableStateFlow(AccountUiState())
    val accountState: StateFlow<AccountUiState> = _accountState.asStateFlow()

    private var accountObserverJob: Job? = null

    init {
        refreshPluginStore()
        startAccountObserver()
    }

    fun refreshPluginStore() {
        viewModelScope.launch(Dispatchers.IO) {
            _storeState.value = _storeState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val success = pluginStore.refresh()
            _storeState.value = if (success) {
                PluginStoreState(
                    plugins = pluginStore.getAvailablePlugins(),
                    isLoading = false,
                    errorMessage = null,
                    lastUpdatedAtMillis = System.currentTimeMillis()
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

    fun installPlugin(plugin: PluginRegistryEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            pluginInstallManager.install(plugin)
            refreshPluginStore()
        }
    }

    fun uninstallPlugin(plugin: PluginRegistryEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            pluginInstallManager.uninstall(plugin.pluginId)
            refreshPluginStore()
        }
    }

    fun startRpc() {
        viewModelScope.launch(Dispatchers.IO) {
            app.rpcManager.connect()
            updateAccountState()
        }
    }

    fun stopRpc() {
        viewModelScope.launch(Dispatchers.IO) {
            app.rpcManager.clearPresence()
            app.rpcManager.disconnect()
            updateAccountState()
        }
    }

    private fun startAccountObserver() {
        accountObserverJob?.cancel()
        accountObserverJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                updateAccountState()
                delay(1000L)
            }
        }
    }

    private fun updateAccountState() {
        val rpcManager = app.rpcManager
        val connected = rpcManager.isConnected()
        val presence = rpcManager.getCurrentPresence()

        _accountState.value = if (connected && presence != null) {
            AccountUiState(
                connected = true,
                activeProvider = presence.providerName,
                activeTitle = presence.title,
                activeTime = formatTime(presence),
                liveStatus = "Active"
            )
        } else {
            AccountUiState(
                connected = connected,
                liveStatus = if (connected) "Active" else "Offline"
            )
        }
    }

    private fun formatTime(presence: Presence): String {
        return if (presence.startTimestamp != null && presence.endTimestamp != null) {
            val elapsed = maxOf(0L, System.currentTimeMillis() - presence.startTimestamp)
            val remaining = maxOf(0L, presence.endTimestamp - System.currentTimeMillis())
            val total = elapsed + remaining
            "${formatMillis(elapsed)} / ${formatMillis(total)}"
        } else {
            "00:00 / 00:00"
        }
    }

    private fun formatMillis(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}
