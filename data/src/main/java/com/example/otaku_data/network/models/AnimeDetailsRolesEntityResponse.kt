package com.example.otaku_data.network.models

import androidx.annotation.Keep
import com.example.otaku_domain.models.details.roles.Character
import com.example.otaku_domain.models.details.roles.Person

@Keep
data class AnimeDetailsRolesEntityResponse(
    val character: Character?,
    val person: Person?
)