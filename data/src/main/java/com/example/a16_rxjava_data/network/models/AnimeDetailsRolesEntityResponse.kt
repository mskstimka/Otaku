package com.example.a16_rxjava_data.network.models

import com.example.a16_rxjava_domain.models.details.roles.Character
import com.example.a16_rxjava_domain.models.details.roles.Person

data class AnimeDetailsRolesEntityResponse(
    val character: Character?,
    val person: Person?,
    val roles: List<String>?,
    val roles_russian: List<String>?
)