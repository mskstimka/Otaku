package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.Character
import com.example.animator_domain.models.details.roles.Person

@Keep
data class AnimeDetailsRolesEntityResponse(
    val character: Character?,
    val person: Person?,
    val roles: List<String>?,
    val roles_russian: List<String>?
)