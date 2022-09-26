package com.example.animator.home.adapters.random

import com.example.animator_domain.models.poster.AnimePosterEntity
import java.util.*

data class ContainerRandom(
    val id: String = UUID.randomUUID().toString(),
    val item: AnimePosterEntity
)