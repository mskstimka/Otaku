package com.example.otaku.home.adapters.random

import com.example.otaku.utils.CountUtils
import com.example.otaku_domain.models.poster.AnimePosterEntity

data class ContainerRandom(
    val id: Int = CountUtils.getId(),
    val item: AnimePosterEntity
)