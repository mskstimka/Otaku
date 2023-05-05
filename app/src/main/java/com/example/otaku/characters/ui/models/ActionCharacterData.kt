package com.example.otaku.characters.ui.models

import com.example.otaku.characters.adapters.info.ContainerCharacterInfo
import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchises
import com.example.otaku.anime.details.info.adapters.persons.ContainerPerson

data class ActionCharacterData(
    val character: List<ContainerCharacterInfo>,
    val seyu: MutableList<ContainerPerson>,
    val franchises: List<ContainerFranchises>
)