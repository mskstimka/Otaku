package com.example.animator_domain.models.details

import androidx.annotation.Keep

@Keep
data class Studio(
    val filtered_name: String,
    val id: Int,
    val image: String,
    val name: String,
    val real: Boolean
)