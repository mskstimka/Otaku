package com.example.otaku.anime.home.adapters.poster

import java.util.*

data class ContainerPoster(
    val id: String = UUID.randomUUID().toString(),
    val action: () -> Unit
    )