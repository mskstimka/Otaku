package com.example.otaku.anime.details.info.adapters.studios

import androidx.annotation.Keep
import com.example.otaku_domain.models.details.Studio

@Keep
data class ContainerStudios(
    val list: List<Studio>,
    val id: String = "studios_id"
)