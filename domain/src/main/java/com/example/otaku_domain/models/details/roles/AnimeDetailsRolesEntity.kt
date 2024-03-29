package com.example.otaku_domain.models.details.roles

import androidx.annotation.Keep

@Keep
data class AnimeDetailsRolesEntity(
    val character: List<Character>,
    val person: List<Person>
)