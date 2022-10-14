package com.example.otaku.home.adapters.favorites

import com.example.animator_domain.models.poster.AnimePosterEntity

data class ContainerFavorites(
    val id: String = "container_favorites",
    val list: List<AnimePosterEntity>
)