package com.example.otaku_domain.models.user

import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.models.user.status.RateStatus

data class Rate(
    val id: Long,
    val score: Int,
    val rateStatus: RateStatus,
    val episodes: Int?,
    val anime: AnimePosterEntity?
)