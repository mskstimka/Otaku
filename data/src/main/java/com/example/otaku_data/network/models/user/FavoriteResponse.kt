package com.example.otaku_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FavoriteResponse(
        @field:SerializedName("id") val id : Long,
        @field:SerializedName("name") val name : String,
        @field:SerializedName("russian") val nameRu : String?,
        @field:SerializedName("image") val image : String,
        @field:SerializedName("url") val url : String?
)