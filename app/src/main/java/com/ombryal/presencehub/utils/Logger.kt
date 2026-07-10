package com.ombryal.presencehub.utils

import android.util.Log

object Logger {
    private const val TAG = "PresenceHub"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(TAG, message, throwable)
    }
}
