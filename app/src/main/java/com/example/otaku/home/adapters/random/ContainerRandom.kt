package com.example.otaku.home.adapters.random

import com.example.otaku.utils.CountUtils
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.google.errorprone.annotations.Keep

@Keep
data class ContainerRandom(
    val id: Int = CountUtils.getId(),
    val item: AnimePosterEntity
)