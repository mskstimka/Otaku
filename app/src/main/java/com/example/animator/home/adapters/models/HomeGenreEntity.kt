package com.example.animator.home.adapters.models

import androidx.annotation.Keep
import com.example.animator_domain.models.poster.AnimePosterEntity
import java.util.*

@Keep
data class HomeGenreEntity(
    val title: String,
    var list: List<AnimePosterEntity> = emptyList()
) : DisplayableItem {
    override val id = UUID.randomUUID().toString()
}