package com.example.animator.details.info.adapters.authors

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.Person
import java.util.*

@Keep
data class ContainerAuthors(
    val list: List<Person>,
    val id: String = UUID.randomUUID().toString()
)