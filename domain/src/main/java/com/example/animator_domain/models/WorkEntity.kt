package com.example.animator_domain.models

import com.example.animator_domain.models.poster.AnimePosterEntity
import java.util.*

data class WorkEntity(
    val anime: AnimePosterEntity,
    val role: String,
    val id: String = UUID.randomUUID().toString()
)