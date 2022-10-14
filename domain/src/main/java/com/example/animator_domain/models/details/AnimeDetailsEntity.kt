package com.example.animator_domain.models.details

import androidx.annotation.Keep
import com.example.animator_domain.models.Image

@Keep
data class AnimeDetailsEntity(
    val aired_on: String?,
    val description: Any?,
    var description_html: String,
    val episodes: Int?,
    val episodes_aired: Int?,
    val genres: List<Genre>,
    val id: Int,
    val image: Image,
    val kind: String?,
    val name: String?,
    var russian: String?,
    val score: String?,
    val screenshots: List<Screenshot>,
    val status: String?,
    val statusColor: String,
    val studios: List<Studio>,
    val videos: List<Video>
)