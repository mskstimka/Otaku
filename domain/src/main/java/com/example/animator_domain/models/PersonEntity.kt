package com.example.animator_domain.models

import androidx.annotation.Keep

@Keep
data class PersonEntity(
    val id: Long,
    val name: String,
    var nameRu: String,
    val nameJp: String?,
    val image: Image?,
    val url: String,
    var jobTitle: String,
    val birthDay: String,
    val works: List<WorkEntity>,
    val roles: List<SeyuWorks>
)