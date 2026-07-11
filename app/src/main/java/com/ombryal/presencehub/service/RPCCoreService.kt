package com.ombryal.presencehub.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.ombryal.presencehub.DiscordRPCHubApp
import com.ombryal.presencehub.utils.Constants
import com.ombryal.presencehub.utils.Logger

class RPCCoreService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var pollingRunnable: Runnable? = null

    override fun onCreate() {
        super.onCreate()

        startForeground(1001, buildNotification())

        val app = application as DiscordRPCHubApp
        app.rpcManager.connect()
        startPolling()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        stopPolling()

        val app = application as DiscordRPCHubApp
        app.rpcManager.clearPresence()
        app.rpcManager.disconnect()

        super.onDestroy()
    }

    private fun startPolling() {
        val app = application as DiscordRPCHubApp

        pollingRunnable = object : Runnable {
            override fun run() {
                try {
                    app.pluginManager.getActivePlugin()?.onPoll()
                    app.rpcManager.refreshPresence()
                } catch (t: Throwable) {
                    Logger.e("Polling error", t)
                }

                handler.postDelayed(this, Constants.DEFAULT_UPDATE_INTERVAL_MS)
            }
        }

        handler.post(pollingRunnable!!)
    }

    private fun stopPolling() {
        pollingRunnable?.let { handler.removeCallbacks(it) }
        pollingRunnable = null
    }

    private fun buildNotification(): Notification {
        val channelId = "presencehub_rpc"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "PresenceHub RPC",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("PresenceHub running")
            .setContentText("Monitoring YouTube presence")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()
    }
}
