package com.example.a16_rxjava_data.models

import com.example.a16_rxjava_domain.models.Image
import com.google.gson.annotations.SerializedName

data class AnimePosterEntityResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: Image,
    @SerializedName("name") val name: String,
    @SerializedName("score") val score: String,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("episodes_aired") val episodesAired: Int,
    @SerializedName("url") val url: String,
    @SerializedName("status") val status: String
)