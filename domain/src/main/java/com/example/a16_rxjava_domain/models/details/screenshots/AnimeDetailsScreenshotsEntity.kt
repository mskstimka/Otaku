package com.example.a16_rxjava_domain.models.details.screenshots

import androidx.annotation.Keep

@Keep
data class AnimeDetailsScreenshotsEntity(
    val original: String,
    val preview: String
)