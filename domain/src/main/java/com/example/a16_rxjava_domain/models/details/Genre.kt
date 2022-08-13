package com.example.a16_rxjava_domain.models.details

import androidx.annotation.Keep

@Keep
data class Genre(
    val id: Int,
    val kind: String,
    val name: String,
    val russian: String
)