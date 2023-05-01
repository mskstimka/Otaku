package com.example.animator_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FavoriteListResponse(
        @SerializedName("animes") val animes: List<FavoriteResponse>,
        @SerializedName("mangas") val mangas: List<FavoriteResponse>,
        @SerializedName("characters") val characters: List<FavoriteResponse>,
        @SerializedName("people") val people: List<FavoriteResponse>,
        @SerializedName("mangakas") val mangakas: List<FavoriteResponse>,
        @SerializedName("seyu") val seyu: List<FavoriteResponse>,
        @SerializedName("producers") val producers: List<FavoriteResponse>
)