package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.example.domain.models.details.roles.Character
import com.example.domain.models.details.roles.Person

@Keep
data class AnimeDetailsRolesEntityResponse(
    val character: Character?,
    val person: Person?
)