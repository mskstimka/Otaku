package com.example.rxjava.home.adapters.models

import androidx.annotation.Keep
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import java.util.*
@Keep
data class HomeGenreEntity(
    val title: String,
    var list: List<AnimePosterEntity> = emptyList()
) : DisplayableItem {
    override val id = UUID.randomUUID().toString()
}