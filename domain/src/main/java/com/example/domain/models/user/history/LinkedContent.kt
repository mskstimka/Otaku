package com.example.domain.models.user.history

import com.example.domain.models.Image

data class LinkedContent(
    var id: Int,
    var name: String,
    var russian: String? = null,
    var image: Image? = null,
)