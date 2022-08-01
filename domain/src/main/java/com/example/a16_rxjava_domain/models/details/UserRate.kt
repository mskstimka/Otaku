package com.example.a16_rxjava_domain.models.details

data class UserRate(
    val chapters: Any,
    val created_at: String,
    val episodes: Int,
    val id: Int,
    val rewatches: Int,
    val score: Int,
    val status: String,
    val text: Any,
    val text_html: String,
    val updated_at: String,
    val volumes: Any
)