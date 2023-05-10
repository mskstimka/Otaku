package com.example.otaku_data.network.models

import androidx.annotation.Keep

@Keep
data class AnimeDetailsScreenshotsEntityResponse(
    val original: String,
    val preview: String
)