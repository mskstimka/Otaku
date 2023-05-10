package com.example.otaku_domain.models.user

import androidx.annotation.Keep

@Keep
data class UserStat(
        val anime : List<Statistic>,
        val manga : List<Statistic>?
)