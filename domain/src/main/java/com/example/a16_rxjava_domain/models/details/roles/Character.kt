package com.example.a16_rxjava_domain.models.details.roles

import com.example.a16_rxjava_domain.models.Image

data class Character(
    val id: Int,
    val image: Image,
    val name: String,
    val russian: String,
    val url: String
)