package com.ombryal.presencehub.providers.youtube

import android.app.Notification

object YouTubeNotificationParser {

    fun parseTitle(notification: Notification): String? {
        val extras = notification.extras
        return extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()?.trim()
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
}
