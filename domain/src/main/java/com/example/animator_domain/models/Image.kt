package com.example.animator_domain.models

import androidx.annotation.Keep

@Keep
data class Image(
    val original: String,
    val preview: String,
    val x48: String,
    val x96: String
)