package com.ombryal.presencehub.providers

import com.ombryal.presencehub.data.models.Presence

interface ProviderTemplate {
    fun isAvailable(): Boolean
    fun start()
    fun stop()
    fun getPresence(): Presence?
}
