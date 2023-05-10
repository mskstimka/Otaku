package com.example.otaku_domain.models.user

import androidx.annotation.Keep

@Keep
data class UserDetails(
        val id: Long,
        val stats: UserStats
)