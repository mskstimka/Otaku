package com.example.a16_rxjava_domain.models.home

import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity


data class PrevPoster(
    val list: List<AnimePosterEntity>
) : DisplayableItem