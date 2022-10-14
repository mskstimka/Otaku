package com.example.otaku.details.info.adapters.characters

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.Character

@Keep
data class ContainerCharacters(
    val list: List<Character>,
    val id: String = "characters_id"
)