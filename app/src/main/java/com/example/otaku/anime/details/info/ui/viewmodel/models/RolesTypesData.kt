package com.example.otaku.anime.details.info.ui.viewmodel.models

import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.anime.details.info.adapters.persons.ContainerPerson

data class RolesTypesData(
    val persons: MutableList<ContainerPerson>,
    val characters: List<ContainerCharacters>
)