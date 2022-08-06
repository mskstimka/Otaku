package com.example.rxjava.home.adapters

import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import java.util.*

data class PrevPoster(
    val list: List<AnimePosterEntity>
) : DisplayableItem {
    override val id = UUID.randomUUID().toString()
}