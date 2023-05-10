package com.example.otaku_domain.models.user

import androidx.annotation.Keep
import com.example.otaku_domain.models.user.status.RateStatus

@Keep
data class RateStatus(
    val id: Long,
    val name: RateStatus,
    val size: Int,
    val type: Type
)