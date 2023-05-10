package com.example.otaku_domain.models.user.history

import com.example.otaku_domain.models.Image

data class LinkedContent(
    var id: Int,
    var name: String,
    var russian: String? = null,
    var image: Image? = null,
    var episodes: Int? = null
)