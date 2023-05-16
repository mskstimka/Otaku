package com.example.otaku.anime.details.episodes.models

import com.google.errorprone.annotations.Keep
import java.util.*

@Keep
data class ContainerEpisodeHeader(
    override val id: String = UUID.randomUUID().toString(),
    val title: String = "Episodes",
    val action: () -> Unit
) : DisplayableItem