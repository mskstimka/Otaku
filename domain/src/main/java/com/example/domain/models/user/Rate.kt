package com.example.domain.models.user

import com.example.domain.models.poster.AnimePosterEntity
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class Rate(
    val id: Long,
    val score: Int,
    val status: RateStatus,
    val episodes: Int?,
    val anime: AnimePosterEntity?
)