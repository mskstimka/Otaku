package com.example.animator_domain.models.characters

import androidx.annotation.Keep
import com.example.animator_domain.models.Image
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.Person
import com.example.animator_domain.models.poster.AnimePosterEntity

@Keep
data class CharacterDetailsEntity(
    val id: Long,
    val name: String,
    val nameRu: String?,
    val image: Image,
    val url: String,
    val nameAlt: String?,
    val nameJp: String?,
    val description: String?,
    val description_html: String,
    val seyu: List<Person>,
    val animes: List<AnimeDetailsFranchisesEntity>
)
