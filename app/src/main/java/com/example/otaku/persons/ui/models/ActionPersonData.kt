package com.example.otaku.persons.ui.models

import com.example.otaku.anime.details.info.adapters.characters.ContainerCharacters
import com.example.otaku.persons.adapters.info.ContainerPersonInfo
import com.example.otaku.persons.adapters.roles.ContainerWorks

data class ActionPersonData(
    val person: List<ContainerPersonInfo>,
    val works: List<ContainerWorks>,
    val characters: List<ContainerCharacters>
)