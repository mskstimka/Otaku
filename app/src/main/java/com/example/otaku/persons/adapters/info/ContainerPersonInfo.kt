package com.example.otaku.persons.adapters.info

import com.example.otaku_domain.models.PersonEntity
import com.google.errorprone.annotations.Keep

@Keep
data class ContainerPersonInfo(
    val id: String = "container_person_info",
    val item: PersonEntity
)
