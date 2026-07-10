package com.ombryal.presencehub.providers.youtube

import com.ombryal.presencehub.data.models.Presence

object YouTubePresenceMapper {

    fun map(mediaData: YouTubeMediaData): Presence {
        val stateText = if (mediaData.isPlaying) "Watching" else "Paused"

        val startTimestamp = mediaData.positionMs?.let { position ->
            System.currentTimeMillis() - position
        }

        val endTimestamp = if (mediaData.durationMs != null && mediaData.positionMs != null) {
            val remaining = mediaData.durationMs - mediaData.positionMs
            if (remaining > 0) System.currentTimeMillis() + remaining else null
        } else {
            null
        }

        return Presence(
            pluginId = "youtube",
            providerName = "YouTube",
            title = mediaData.title,
            subtitle = mediaData.channel,
            state = stateText,
            largeImageKey = "youtube_logo",
            smallImageKey = if (mediaData.isPlaying) "play" else "pause",
            startTimestamp = startTimestamp,
            endTimestamp = endTimestamp,
            buttonLabel = "Watch on YouTube",
            buttonUrl = mediaData.videoUrl
        )
    }
}
