package com.example.otaku_data.network.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TranslationResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("kind") val _kind: String?,
    @SerializedName("targetId") val targetId: Long,
    @SerializedName("episode") val episode: Int,
    @SerializedName("url") val url: String,
    @SerializedName("hosting") val hosting: String,
    @SerializedName("language") val language: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("quality") val _quality: String?,
    @SerializedName("episodesTotal") val episodesTotal: Int?
)