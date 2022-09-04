package com.example.animator_data.network.models

import androidx.annotation.Keep

@Keep
data class Node(
    val date: Int?,
    val id: Int,
    val image_url: String?,
    val kind: String?,
    val name: String?,
    val url: String?,
    val weight: Int?,
    val year: Int?
)