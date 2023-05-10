package com.example.otaku_data.network.models.user

import com.google.gson.annotations.SerializedName

enum class TypeResponse {
    @SerializedName("Anime")
    ANIME,

    @SerializedName("Manga")
    MANGA,

    @SerializedName("Ranobe")
    RANOBE,

    @SerializedName("Character")
    CHARACTER,

    @SerializedName("Person")
    PERSON,

    @SerializedName("User")
    USER,

    @SerializedName("Club")
    CLUB,

    @SerializedName("ClubPage")
    CLUB_PAGE,

    @SerializedName("Collection")
    COLLECTION,

    @SerializedName("Review")
    REVIEW,

    @SerializedName("CosplayGallery")
    COSPLAY,

    @SerializedName("Contest")
    CONTEST,

    @SerializedName("Topic")
    TOPIC,

    @SerializedName("Comment")
    COMMENT,

    @SerializedName("")
    UNKNOWN,

}