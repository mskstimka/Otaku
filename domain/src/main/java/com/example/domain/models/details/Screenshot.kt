package com.example.domain.models.details

import androidx.annotation.Keep

@Keep
data class Screenshot(
    val original: String,
    val preview: String
)