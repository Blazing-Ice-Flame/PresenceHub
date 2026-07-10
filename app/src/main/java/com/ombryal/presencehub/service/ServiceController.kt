package com.ombryal.presencehub.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

object ServiceController {

    fun startRpcService(context: Context) {
        val intent = Intent(context, RPCCoreService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }

    fun stopRpcService(context: Context) {
        val intent = Intent(context, RPCCoreService::class.java)
        context.stopService(intent)
    }
}
