package com.example.otaku_data.network.models

import androidx.annotation.Keep
import com.example.otaku_data.network.models.user.status.UserRateResponse
import com.example.otaku_domain.models.Image
import com.example.otaku_domain.models.details.*
import com.google.gson.annotations.SerializedName

@Keep
data class AnimeDetailsEntityResponse(
    val aired_on: String?,
    val description: Any?,
    val description_html: String,
    val episodes: Int?,
    val episodes_aired: Int?,
    val genres: List<Genre>,
    val id: Int,
    val image: Image,
    val kind: String?,
    val name: String?,
    val russian: String?,
    val score: String?,
    val screenshots: List<Screenshot>,
    val status: String?,
    val studios: List<Studio>,
    val videos: List<Video>,
    @SerializedName("user_rate") val userRate: UserRateResponse?,
)