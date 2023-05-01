package com.example.domain.models.user

import androidx.annotation.Keep

@Keep
data class Favorite(
        val id: Long,
        val name: String,
        val nameRu: String?,
        val image: String,
        val url: String?,
        val type: FavoriteType
)