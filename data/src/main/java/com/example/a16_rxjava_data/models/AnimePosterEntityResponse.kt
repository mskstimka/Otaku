package com.example.a16_rxjava_data.models

import com.example.a16_rxjava_domain.models.Image
import com.google.gson.annotations.SerializedName

data class AnimePosterEntityResponse(
    val id: Int,
    val image: Image,
    val name: String,
    val score: String,
    val episodes: Int,
    @SerializedName("episodes_aired") val episodesAired: Int,
    val url: String,
    val status: String,
    val russian: String
)