package com.example.otaku.home.adapters.favorites

import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.google.errorprone.annotations.Keep

@Keep
data class ContainerFavorites(
    val id: String = "container_favorites",
    val list: List<AnimePosterEntity>
)