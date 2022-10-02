package com.example.animator_domain.models.details

data class Translation(
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