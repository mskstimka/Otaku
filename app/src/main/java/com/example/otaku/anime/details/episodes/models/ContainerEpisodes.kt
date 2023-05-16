package com.example.otaku.anime.details.episodes.models

import com.google.errorprone.annotations.Keep
import java.util.*

@Keep
data class ContainerEpisodes(
    override val id: String = UUID.randomUUID().toString(),
    val episode: Int,
    var isLast: Boolean = false
) : DisplayableItem