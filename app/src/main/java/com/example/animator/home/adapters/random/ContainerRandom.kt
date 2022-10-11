package com.example.animator.home.adapters.random

import com.example.animator.utils.CountUtls
import com.example.animator_domain.models.poster.AnimePosterEntity
import java.util.*

data class ContainerRandom(
    val id: Int = CountUtls.getId(),
    val item: AnimePosterEntity
)