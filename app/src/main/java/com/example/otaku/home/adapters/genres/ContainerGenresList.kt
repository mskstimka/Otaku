package com.example.otaku.home.adapters.genres

import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.google.errorprone.annotations.Keep
import java.util.*

@Keep
data class ContainerGenresList(
    val title: Int,
    var list: List<AnimePosterEntity> = emptyList(),
    val id: String = UUID.randomUUID().toString()
)