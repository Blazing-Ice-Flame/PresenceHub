package com.ombryal.presencehub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ombryal.presencehub.plugins.InstalledPluginRegistry
import com.ombryal.presencehub.plugins.PluginInstallManager
import com.ombryal.presencehub.plugins.PluginRegistryEntry
import com.ombryal.presencehub.plugins.PluginStore
import com.ombryal.presencehub.plugins.PluginStoreState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val installedPluginRegistry = InstalledPluginRegistry(application)
    private val pluginStore = PluginStore(installedPluginRegistry = installedPluginRegistry)
    private val pluginInstallManager = PluginInstallManager(
        installedPluginRegistry = installedPluginRegistry
    )

    private val _storeState = MutableStateFlow(PluginStoreState(isLoading = true))
    val storeState: StateFlow<PluginStoreState> = _storeState.asStateFlow()

    init {
        refreshPluginStore()
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
}
