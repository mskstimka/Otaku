package com.example.a16_rxjava_domain.models.poster


data class AnimePosterEntity(
    val id: Int,
    val image: Image,
    val name: String,
    val score: String,
    val episodes: Int,
    val episodesAired: Int,
    val url: String,
    val status: String
)