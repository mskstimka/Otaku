package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.example.domain.models.Image

@Keep
data class CharacterDetailsSimiliarResponse(
    val aired_on: String?,
    val id: Int,
    val image: Image?,
    val kind: String?,
    val name: String?,
    val url: String?,
)