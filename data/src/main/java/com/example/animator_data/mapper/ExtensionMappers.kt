package com.example.animator_data.mapper

import com.example.animator_data.database.models.LocalAnimePosterEntity
import com.example.animator_data.network.models.*
import com.example.domain.*
import com.example.domain.models.PersonEntity
import com.example.domain.models.SeyuWorks
import com.example.domain.models.WorkEntity
import com.example.domain.models.characters.CharacterDetailsEntity
import com.example.domain.models.details.AnimeDetailsEntity
import com.example.domain.models.details.Translations
import com.example.domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.domain.models.details.roles.Character
import com.example.domain.models.details.roles.Person
import com.example.domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.domain.models.poster.AnimePosterEntity


fun AnimeDetailsEntityResponse.toAnimeDetailsEntity(): AnimeDetailsEntity = AnimeDetailsEntity(
    aired_on = this.aired_on,
    description = this.description,
    description_html = this.description_html,
    episodes = this.episodes,
    episodes_aired = this.episodes_aired,
    genres = MapperUtils.checkGenresAdapter(this.genres),
    id = this.id,
    image = this.image,
    kind = this.kind,
    name = this.name,
    russian = this.russian,
    score = this.score,
    screenshots = this.screenshots,
    status = this.status,
    statusColor = MapperUtils.checkStatusColor(this.status),
    studios = MapperUtils.checkStudioList(this.studios),
    videos = MapperUtils.checkVideoList(this.videos)
)


fun List<AnimeDetailsScreenshotsEntityResponse>.toAnimeDetailsScreenshotsEntity(): List<AnimeDetailsScreenshotsEntity> {

    var screenshots: MutableList<AnimeDetailsScreenshotsEntity> = mutableListOf()

    when (this) {
        emptyList<AnimeDetailsScreenshotsEntityResponse>() -> {
            screenshots = mutableListOf(DEFAULT_SCREENSHOT)
        }
        else -> this.forEach {
            screenshots.add(
                AnimeDetailsScreenshotsEntity(
                    original = it.original,
                    preview = it.preview
                )
            )
        }
    }

    return screenshots
}


fun AnimeDetailsFranchisesEntityResponse.toListAnimeDetailsFranchisesEntity(): List<AnimeDetailsFranchisesEntity> {
    var list: MutableList<AnimeDetailsFranchisesEntity> = mutableListOf()

    when (this.nodes) {
        emptyList<Node>() -> list = mutableListOf(DEFAULT_FRANCHISE)
        else -> this.nodes.forEach {
            list.add(
                AnimeDetailsFranchisesEntity(
                    date = it.date,
                    id = it.id,
                    image_url = it.image_url,
                    kind = it.kind,
                    name = it.name,
                    url = it.image_url,
                    year = it.year
                )
            )
        }

    }
    return list
}


fun List<AnimeDetailsRolesEntityResponse>.toListAnimeRolesEntity(): AnimeDetailsRolesEntity {

    val personNotNull = this.mapNotNull { it.person }
    val listPerson = if (personNotNull == emptyList<Person>()) {
        listOf(DEFAULT_PERSON)
    } else personNotNull

    val characterNotNull = this.mapNotNull { it.character }
    val listCharacters = if (characterNotNull == emptyList<Character>()) {
        listOf(DEFAULT_CHARACTER)
    } else characterNotNull


    return AnimeDetailsRolesEntity(character = listCharacters.reversed(), person = listPerson)
}


fun CharacterDetailsResponse.toCharacterDetailsEntity(): CharacterDetailsEntity {
    return CharacterDetailsEntity(
        this.id,
        this.name,
        this.nameRu,
        this.image,
        this.url,
        this.nameAlt,
        this.nameJp,
        this.description,
        this.description_html,
        this.seyu,
        this.animes.toListFrachisesEntity()
    )
}


fun List<WorkResponse?>.toListWorkEntity(): List<WorkEntity> {

    return if (this.isNotEmpty()) {
        this.map {
            if (it?.anime != null) {
                WorkEntity(
                    anime = it.anime.toAnimePosterEntity(),
                    role = it.role
                )
            } else {
                null
            }
        }.filterNotNull()
    } else {
        emptyList()
    }
}


fun List<SeyuWorksResponse>.toListSeyuWork(): List<SeyuWorks> {
    return if (this.isNotEmpty()) {
        this.map { SeyuWorks(characters = it.characters) }
    } else {
        emptyList()
    }
}

fun PersonResponse.toPersonEntity(): PersonEntity {
    return PersonEntity(
        id = this.id,
        name = this.name,
        nameRu = this.nameRu ?: "",
        nameJp = this.nameJp ?: "",
        image = this.image,
        url = this.url ?: "",
        jobTitle = this.jobTitle ?: "",
        birthDay = "${this.birthDay?.day}.${this.birthDay?.month}.${this.birthDay?.year}",
        works = this.works?.toListWorkEntity() ?: emptyList(),
        roles = this.roles?.toListSeyuWork() ?: emptyList()
    )
}

fun List<CharacterDetailsSimiliarResponse>.toListFrachisesEntity(): List<AnimeDetailsFranchisesEntity> {
    return this.map {
        AnimeDetailsFranchisesEntity(
            id = it.id,
            image_url = SHIKIMORI_URL + it.image?.original,
            date = null,
            kind = it.kind,
            name = it.name,
            url = it.url,
            year = null
        )
    }
}

fun List<AnimePosterEntityResponse>.toListAnimePosterEntity(): List<AnimePosterEntity> {
    return this.map {
        AnimePosterEntity(
            id = it.id,
            image = it.image,
            name = it.name,
            score = it.score,
            episodes = it.episodes,
            episodesAired = it.episodesAired,
            url = it.url,
            status = it.status,
            statusColor = MapperUtils.checkStatusColor(it.status),
            russian = it.russian
        )
    }
}

fun AnimePosterEntity.toLocalListAnimePosterEntity(): LocalAnimePosterEntity {
    return LocalAnimePosterEntity(
        id = this.id,
        image = this.image,
        name = this.name,
        score = this.score,
        episodes = this.episodes,
        episodesAired = this.episodesAired,
        url = this.url,
        status = this.status,
        russian = this.russian
    )
}

fun List<LocalAnimePosterEntity>.localToListAnimePosterEntity(): List<AnimePosterEntity> {
    return this.map {
        AnimePosterEntity(
            id = it.id,
            image = it.image,
            name = it.name,
            score = it.score,
            episodes = it.episodes,
            episodesAired = it.episodesAired,
            url = it.url,
            status = it.status,
            statusColor = MapperUtils.checkStatusColor(it.status),
            russian = it.russian
        )
    }
}

fun AnimePosterEntityResponse.toAnimePosterEntity(): AnimePosterEntity {
    return AnimePosterEntity(
        id = this.id,
        image = this.image,
        name = this.name,
        score = this.score,
        episodes = this.episodes,
        episodesAired = this.episodesAired,
        url = this.url,
        status = this.status,
        statusColor = MapperUtils.checkStatusColor(this.status),
        russian = this.russian
    )
}

fun TranslationResponse.toTranslations(): Translations {
    return Translations(
        id = this.id,
        _kind = this._kind,
        targetId = this.targetId,
        episode = this.episode,
        url = this.url,
        hosting = this.hosting,
        language = this.language,
        author = this.author,
        _quality = this._quality,
        episodesTotal = this.episodesTotal
    )
}



