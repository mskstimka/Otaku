package com.example.otaku.persons.adapters.roles

import com.example.otaku_domain.models.WorkEntity

data class ContainerWorks(
    val id: String = "container_works",
    val list: List<WorkEntity>
)