package com.example.otaku_domain.models.user.status

import androidx.annotation.Keep


@Keep
data class UserRate(
    val id: Long? = null,
    var userId: Long? = null,
    var targetId: Long? = null,
    var targetType: String? = null,
    val score: Double? = null,
    var status: String? = null,
    val rewatches: Int? = null,
    var episodes: Int? = null,
    val volumes: Int? = null,
    val chapters: Int? = null,
    val text: String? = null,
)