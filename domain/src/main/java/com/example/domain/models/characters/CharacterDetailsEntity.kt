package com.example.domain.models.characters

import androidx.annotation.Keep
import com.example.domain.models.Image
import com.example.domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.domain.models.details.roles.Person

@Keep
data class CharacterDetailsEntity(
    val id: Long,
    val name: String,
    var nameRu: String?,
    val image: Image,
    val url: String,
    val nameAlt: String?,
    val nameJp: String?,
    val description: String?,
    var description_html: String,
    val seyu: List<Person>,
    val animes: List<AnimeDetailsFranchisesEntity>
)
