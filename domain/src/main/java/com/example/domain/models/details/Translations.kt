package com.example.domain.models.details

import androidx.annotation.Keep

@Keep
data class Translations(
    val id: Long,
    private val _kind: String?,
    val targetId: Long,
    val episode: Int,
    val url: String,
    val hosting: String,
    val language: String?,
    val author: String?,
    private val _quality: String?,
    val episodesTotal: Int?
)