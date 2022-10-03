package com.example.animator.details.info.adapters.characters

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.Character
import java.util.*

@Keep
data class ContainerCharacters(
    val list: List<Character>,
    val id: String = UUID.randomUUID().toString()
)