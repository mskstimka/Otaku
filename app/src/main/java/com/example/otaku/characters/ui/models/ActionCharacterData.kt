package com.example.otaku.characters.ui.models

import com.example.otaku.anime.details.info.adapters.franchises.ContainerFranchises
import com.example.otaku.anime.details.info.adapters.persons.ContainerPerson
import com.example.otaku.characters.adapters.info.ContainerCharacterInfo

data class ActionCharacterData(
    val character: List<ContainerCharacterInfo>,
    val seyu: MutableList<ContainerPerson>,
    val franchises: List<ContainerFranchises>
)