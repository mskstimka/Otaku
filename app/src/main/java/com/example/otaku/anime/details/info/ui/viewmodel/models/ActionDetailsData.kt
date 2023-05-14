package com.example.otaku.anime.details.info.ui.viewmodel.models

import com.example.otaku.anime.details.info.adapters.studios.ContainerStudios
import com.example.otaku.anime.details.info.adapters.videos.ContainerVideos
import com.example.otaku_domain.models.details.AnimeDetailsEntity

data class ActionDetailsData(
    val details: MutableList<AnimeDetailsEntity>,
    val videos: List<ContainerVideos>,
    val studios: List<ContainerStudios>
)