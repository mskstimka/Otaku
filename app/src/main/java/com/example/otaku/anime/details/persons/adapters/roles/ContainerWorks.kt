package com.example.otaku.anime.details.persons.adapters.roles

import com.example.animator_domain.models.WorkEntity

data class ContainerWorks(
    val id: String = "container_works",
    val list: List<WorkEntity>
)