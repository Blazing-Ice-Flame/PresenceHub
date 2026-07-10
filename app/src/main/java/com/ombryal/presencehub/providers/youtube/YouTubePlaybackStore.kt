package com.ombryal.presencehub.providers.youtube

object YouTubePlaybackStore {

    @Volatile
    private var latestData: YouTubeMediaData? = null

    fun update(data: YouTubeMediaData?) {
        latestData = data
    }

    fun get(): YouTubeMediaData? = latestData

    fun clear() {
        latestData = null
    }
}
