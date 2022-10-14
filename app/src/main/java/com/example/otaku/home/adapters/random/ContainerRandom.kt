package com.example.otaku.home.adapters.random

import com.example.otaku.utils.CountUtls
import com.example.animator_domain.models.poster.AnimePosterEntity

data class ContainerRandom(
    val id: Int = CountUtls.getId(),
    val item: AnimePosterEntity
)