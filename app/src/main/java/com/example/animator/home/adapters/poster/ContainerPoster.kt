package com.example.animator.home.adapters.poster

import java.util.*

data class ContainerPoster(
    val id: String = UUID.randomUUID().toString(),
    val action: () -> Unit
    )