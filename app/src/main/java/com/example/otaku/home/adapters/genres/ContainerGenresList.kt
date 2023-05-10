package com.example.otaku.home.adapters.genres

import com.example.otaku_domain.models.poster.AnimePosterEntity
import java.util.*

data class ContainerGenresList(
    val title: Int,
    var list: List<AnimePosterEntity> = emptyList(),
    val id: String = UUID.randomUUID().toString()
)