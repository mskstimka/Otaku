package com.example.otaku.anime.details.info.adapters.videos

import androidx.annotation.Keep
import com.example.otaku_domain.models.details.Video

@Keep
data class ContainerVideos(
    val list: List<Video>,
    val id: String = "videos_id"
)