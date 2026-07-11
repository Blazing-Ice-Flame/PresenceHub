package com.ombryal.presencehub.providers.youtube

import android.app.Notification

object YouTubeNotificationParser {

    fun parseTitle(notification: Notification): String? {
        return notification.extras
            .getCharSequence(Notification.EXTRA_TITLE)
            ?.toString()
            ?.trim()
            ?.takeIf { it.isNotBlank() }
    }

    fun parseChannel(notification: Notification): String? {
        val extras = notification.extras
        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString()?.trim()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()?.trim()

        return when {
            !subText.isNullOrBlank() -> subText
            !text.isNullOrBlank() -> text
            else -> null
        }
    }

    fun isLikelyYouTubeMedia(notification: Notification): Boolean {
        val extras = notification.extras
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()?.trim().orEmpty()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()?.trim().orEmpty()
        return title.isNotBlank() || text.isNotBlank()
    }
}
