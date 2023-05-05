package com.example.otaku.anime.details.info.ui.viewmodel.models

import com.example.domain.models.details.AnimeDetailsEntity
import com.example.otaku.anime.details.info.adapters.studios.ContainerStudios
import com.example.otaku.anime.details.info.adapters.videos.ContainerVideos

data class ActionDetailsData(
    val details: List<AnimeDetailsEntity>,
    val videos: List<ContainerVideos>,
    val studios: List<ContainerStudios>
)