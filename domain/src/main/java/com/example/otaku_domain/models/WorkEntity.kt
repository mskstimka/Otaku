package com.example.otaku_domain.models

import androidx.annotation.Keep
import com.example.otaku_domain.models.poster.AnimePosterEntity
import java.util.*

@Keep
data class WorkEntity(
    val anime: AnimePosterEntity,
    val role: String,
    val id: String = UUID.randomUUID().toString()
)