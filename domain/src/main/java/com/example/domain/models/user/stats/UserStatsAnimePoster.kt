package com.example.domain.models.user.stats

import androidx.annotation.Keep
import com.example.domain.models.Image

@Keep
data class UserStatsAnimePoster(
    val id: Int,
    val image: Image,
    val name: String,
    val score: String,
    val episodes: Int,
    val episodesAired: Int,
    val url: String,
    val status: String,
    val statusColor: String,
    val russian: String,
    val episodeWatched: Int
)