package com.ombryal.presencehub.providers.youtube

class YouTubeDetector {

    fun detect(): YouTubeMediaData? {
        return YouTubePlaybackStore.get()
    }
}
