package com.example.animator_data.network.models

import com.example.animator_domain.models.Image
import com.google.gson.annotations.SerializedName

data class CharacterDetailsSimiliarResponse(
    val aired_on: String?,
    val id: Int,
    val image: Image?,
    val kind: String?,
    val name: String?,
    val url: String?,
)