package com.example.otaku.characters.adapters.info

import com.example.otaku_domain.models.characters.CharacterDetailsEntity

data class ContainerCharacterInfo(
    val id: String = "container_character_info",
    val item: CharacterDetailsEntity
)