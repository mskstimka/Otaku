package com.example.otaku.anime.details.info.adapters.persons

import androidx.annotation.Keep
import com.example.domain.models.details.roles.Person

@Keep
data class ContainerPerson(
    val list: List<Person>,
    val id: String = "authors_id",
    val title: String,
)