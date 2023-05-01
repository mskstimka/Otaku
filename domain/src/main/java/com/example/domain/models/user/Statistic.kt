package com.example.domain.models.user

import androidx.annotation.Keep

@Keep
data class Statistic(
        val name : String,
        val value : Int
)