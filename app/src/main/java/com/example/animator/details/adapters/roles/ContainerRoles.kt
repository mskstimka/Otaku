package com.example.animator.details.adapters.roles

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import java.util.*

@Keep
data class ContainerRoles(
    val list: List<AnimeDetailsRolesEntity>,
    val id: String = UUID.randomUUID().toString()
)