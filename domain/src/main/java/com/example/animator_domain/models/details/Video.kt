package com.example.animator_domain.models.details

import androidx.annotation.Keep

@Keep
data class Video(
    val hosting: String,
    val id: Int,
    val image_url: String,
    val kind: String,
    val name: String,
    val player_url: String,
    val url: String
)