package com.example.otaku_data.network.models

import androidx.annotation.Keep
import com.example.otaku_data.network.models.user.ImageResponse
import com.google.gson.annotations.SerializedName

@Keep
data class AnimePosterEntityResponse(
    val id: Int,
    val image: ImageResponse,
    val name: String,
    val score: String,
    val episodes: Int,
    @SerializedName("episodes_aired") val episodesAired: Int,
    val url: String,
    val status: String,
    val russian: String
)