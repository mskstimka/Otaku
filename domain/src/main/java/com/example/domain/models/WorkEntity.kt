package com.example.domain.models

import androidx.annotation.Keep
import com.example.domain.models.poster.AnimePosterEntity
import java.util.*

@Keep
data class WorkEntity(
    val anime: AnimePosterEntity,
    val role: String,
    val id: String = UUID.randomUUID().toString()
)