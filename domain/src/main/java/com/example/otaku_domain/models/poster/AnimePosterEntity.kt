package com.example.otaku_domain.models.poster

import androidx.annotation.Keep
import com.example.otaku_domain.models.Image

@Keep
data class AnimePosterEntity(
    val id: Int,
    val image: Image,
    val name: String,
    val score: String,
    val episodes: Int,
    val episodesAired: Int,
    val url: String,
    val status: String,
    val statusColor: String,
    val russian: String
)