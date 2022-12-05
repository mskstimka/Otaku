package com.example.otaku.anime.details.episodes.models

import java.util.*

data class ContainerEpisodes(
    override val id: String = UUID.randomUUID().toString(),
    val episode: Int
) : DisplayableItem