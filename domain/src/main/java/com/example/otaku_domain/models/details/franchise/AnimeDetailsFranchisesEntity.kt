package com.example.otaku_domain.models.details.franchise

import androidx.annotation.Keep

@Keep
data class AnimeDetailsFranchisesEntity(
    val date: Int?,
    val id: Int,
    val image_url: String?,
    val kind: String?,
    val name: String?,
    val url: String?,
    val year: Int?
)