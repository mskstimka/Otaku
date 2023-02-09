package com.example.otaku.anime.home.adapters.favorites

import com.example.domain.models.poster.AnimePosterEntity

data class ContainerFavorites(
    val id: String = "container_favorites",
    val list: List<AnimePosterEntity>
)