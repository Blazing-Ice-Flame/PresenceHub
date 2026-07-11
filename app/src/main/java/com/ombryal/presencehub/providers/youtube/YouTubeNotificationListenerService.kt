package com.ombryal.presencehub.providers.youtube

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.ombryal.presencehub.utils.Logger

class YouTubeNotificationListenerService : NotificationListenerService() {

    companion object {
        private const val YOUTUBE_PACKAGE = "com.google.android.youtube"
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Logger.d("YouTubeNotificationListenerService connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != YOUTUBE_PACKAGE) return

        val notification = sbn.notification
        if (!YouTubeNotificationParser.isLikelyYouTubeMedia(notification)) return

        val title = YouTubeNotificationParser.parseTitle(notification) ?: return
        val channel = YouTubeNotificationParser.parseChannel(notification)

        val mediaData = YouTubeMediaData(
            title = title,
            channel = channel,
            videoUrl = null,
            thumbnailUrl = null,
            durationMs = null,
            positionMs = null,
            isPlaying = true
        )

        YouTubePlaybackStore.update(mediaData)
        Logger.d("YouTube notification captured: $title")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        if (sbn.packageName == YOUTUBE_PACKAGE) {
            YouTubePlaybackStore.clear()
            Logger.d("YouTube notification removed")
        }
    }
}
