package com.example.otaku_domain.models.user

import androidx.annotation.Keep

@Keep
data class UserBrief(
    val id: Long,
    val nickname: String,
    val avatar: String?,
    val image: UserImage,
    val name: String?,
)