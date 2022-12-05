package com.example.otaku.anime.details.episodes.models

import java.util.*

data class ContainerEpisodeHeader(
    override val id: String = UUID.randomUUID().toString(),
    val title: String = "Episodes",
    val action: () -> Unit
) : DisplayableItem