package com.example.domain.models.user

import androidx.annotation.Keep

@Keep
data class Status(
        val id: Long,
        val name: RateStatus,
        val size: Int,
        val type: Type
)