package com.example.otaku_domain.models.user

import androidx.annotation.Keep

@Keep
enum class Type(val typeName: String) {
    ANIME("Anime"),
    MANGA("Manga"),
    RANOBE("Ranobe"),
    CHARACTER("Character"),
    PERSON("Person"),
    USER("User"),
    CLUB("Club"),
    CLUB_PAGE("Club_Page"),
    COLLECTION("Collection"),
    REVIEW("Review"),
    COSPLAY("Cosplay"),
    CONTEST("Contest"),
    TOPIC("Topic"),
    COMMENT("Comment"),
    UNKNOWN("Unknown"),
}