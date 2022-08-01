package com.example.a16_rxjava_data.models

data class Node(
    val date: Int,
    val id: Int,
    val image_url: String,
    val kind: String,
    val name: String,
    val url: String,
    val weight: Int,
    val year: Int
)