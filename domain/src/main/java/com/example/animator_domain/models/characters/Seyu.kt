package com.example.animator_domain.models.characters

import androidx.annotation.Keep
import com.example.animator_domain.models.Image


@Keep
data class Seyu(
    val id: Long,
    val name: String,
    val nameRu: String?,
    val image: Image,
    val url: String
)
