package com.example.otaku_data.network.models

import androidx.annotation.Keep
import com.example.otaku_domain.models.Image
import com.example.otaku_domain.models.details.roles.Person
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("russian") val nameRu: String?,
    @SerializedName("image") val image: Image,
    @SerializedName("url") val url: String,
    @SerializedName("altname") val nameAlt: String?,
    @SerializedName("japanese") val nameJp: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("description_html") val description_html: String,
    @SerializedName("seyu") val seyu: List<Person>,
    @SerializedName("animes") val animes: List<CharacterDetailsSimiliarResponse>
)