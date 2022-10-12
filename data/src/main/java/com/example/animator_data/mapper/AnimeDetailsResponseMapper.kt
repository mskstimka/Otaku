package com.example.animator_data.mapper

import android.util.Log
import com.example.animator_data.network.models.*
import com.example.animator_domain.*
import com.example.animator_domain.models.Image
import com.example.animator_domain.models.PersonEntity
import com.example.animator_domain.models.SeyuWorks
import com.example.animator_domain.models.WorkEntity
import com.example.animator_domain.models.characters.CharacterDetailsEntity
import com.example.animator_domain.models.details.AnimeDetailsEntity
import com.example.animator_domain.models.details.Genre
import com.example.animator_domain.models.details.Studio
import com.example.animator_domain.models.details.Video
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.animator_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.animator_domain.models.details.roles.Character
import com.example.animator_domain.models.details.roles.Person
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.google.gson.annotations.SerializedName

object AnimeDetailsResponseMapper {

    private val defaultCharacter =
        Character(
            id = 404,
            image = Image(
                original = NOT_FOUND_TEXT,
                preview = NOT_FOUND_TEXT,
                x48 = NOT_FOUND_TEXT,
                x96 = NOT_FOUND_TEXT
            ),
            name = NOT_FOUND_TEXT, russian = NOT_FOUND_TEXT, url = NOT_FOUND_TEXT
        )

    private val defaultPerson =
        Person(
            id = 404,
            image = Image(
                original = NOT_FOUND_TEXT,
                preview = NOT_FOUND_TEXT,
                x48 = NOT_FOUND_TEXT,
                x96 = NOT_FOUND_TEXT
            ),
            name = NOT_FOUND_TEXT, russian = NOT_FOUND_TEXT, url = NOT_FOUND_TEXT
        )


    private val defaultVideo = Video(
        hosting = NOT_FOUND_TEXT, id = 404, image_url = "http", kind = NOT_FOUND_TEXT,
        name = NOT_FOUND_TEXT, player_url = NOT_FOUND_TEXT, url = NOT_FOUND_TEXT
    )
    private val defaultStudio = Studio(
        filtered_name = NOT_FOUND_TEXT,
        id = 404,
        image = NOT_FOUND_TEXT,
        name = NOT_FOUND_TEXT,
        real = false
    )
    private val defaultGenre = Genre(
        id = 404,
        kind = NOT_FOUND_TEXT,
        name = NOT_FOUND_TEXT,
        russian = NOT_FOUND_TEXT
    )
    private val defaultScreenshot = AnimeDetailsScreenshotsEntity(
        original = NOT_FOUND_TEXT,
        preview = NOT_FOUND_TEXT
    )
    private val defaultFranchise =
        AnimeDetailsFranchisesEntity(
            date = 404, id = 0, image_url = "x96", kind = NOT_FOUND_TEXT, name = NOT_FOUND_TEXT,
            url = NOT_FOUND_TEXT, year = 404
        )


    fun toAnimeDetailsEntity(item: AnimeDetailsEntityResponse) = AnimeDetailsEntity(
        aired_on = item.aired_on,
        description = item.description,
        description_html = item.description_html,
        episodes = item.episodes,
        episodes_aired = item.episodes_aired,
        genres = checkGenresAdapter(item.genres),
        id = item.id,
        image = item.image,
        kind = item.kind,
        name = item.name,
        russian = item.russian,
        score = item.score,
        screenshots = item.screenshots,
        status = item.status,
        statusColor = checkStatusColor(item.status),
        studios = checkStudioList(item.studios),
        videos = checkVideoList(item.videos)
    )

    private fun checkStatusColor(status: String?): String = when (status) {
        ONGOING_STATUS -> BLUE_STATUS_COLOR
        ANONS_STATUS -> RED_STATUS_COLOR
        else -> GREEN_STATUS_COLOR
    }


    fun toAnimeScreenshotsEntity(list: List<AnimeDetailsScreenshotsEntityResponse>): List<AnimeDetailsScreenshotsEntity> {

        var screenshots: MutableList<AnimeDetailsScreenshotsEntity> = mutableListOf()

        when (list) {
            emptyList<AnimeDetailsScreenshotsEntityResponse>() -> {
                screenshots = mutableListOf(defaultScreenshot)
            }
            else -> list.forEach {
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

    fun toListAnimeFranchisesEntity(item: AnimeDetailsFranchisesEntityResponse): List<AnimeDetailsFranchisesEntity> {

        var list: MutableList<AnimeDetailsFranchisesEntity> = mutableListOf()

        when (item.nodes) {
            emptyList<Node>() -> list = mutableListOf(defaultFranchise)
            else -> item.nodes.forEach {
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

    fun toListAnimeRolesEntity(list: List<AnimeDetailsRolesEntityResponse>): AnimeDetailsRolesEntity {

        val personNotNull = list.mapNotNull { it.person }
        val listPerson = if (personNotNull == emptyList<Person>()) {
            listOf(defaultPerson)
        } else personNotNull

        val characterNotNull = list.mapNotNull { it.character }
        val listCharacters = if (characterNotNull == emptyList<Character>()) {
            listOf(defaultCharacter)
        } else characterNotNull



        return AnimeDetailsRolesEntity(character = listCharacters.reversed(), person = listPerson)
    }

    private fun checkVideoList(list: List<Video>): List<Video> {
        return when (list) {
            emptyList<Video>() -> listOf(defaultVideo)
            else -> list
        }
    }

    private fun checkStudioList(list: List<Studio>): List<Studio> {
        return when (list) {
            emptyList<Studio>() -> listOf(defaultStudio)
            else -> list
        }
    }

    private fun checkGenresAdapter(list: List<Genre>): List<Genre> {
        return when (list) {
            emptyList<Genre>() -> listOf(defaultGenre)
            else -> list
        }
    }


    fun toCharacterDetailsEntity(item: CharacterDetailsResponse): CharacterDetailsEntity {
        return CharacterDetailsEntity(
            item.id,
            item.name,
            item.nameRu,
            item.image,
            item.url,
            item.nameAlt,
            item.nameJp,
            item.description,
            item.description_html,
            item.seyu,
            toFranchisesEntity(item.animes)
        )
    }

    private fun toFranchisesEntity(list: List<CharacterDetailsSimiliarResponse>): List<AnimeDetailsFranchisesEntity> =
        list.map {
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

    fun toPersonEntity(item: PersonResponse): PersonEntity {
        return PersonEntity(
            id = item.id,
            name = item.name ?: "",
            nameRu = item.nameRu ?: "",
            nameJp = item.nameJp ?: "",
            image = item.image,
            url = item.url ?: "",
            jobTitle = item.jobTitle ?: "",
            birthDay = "${item.birthDay?.day}.${item.birthDay?.month}.${item.birthDay?.year}",
            works = toWorksEntity(item.works ?: emptyList()),
            roles = toSeyuWorks(item.roles ?: emptyList())
        )
    }

    private fun toWorksEntity(list: List<WorkResponse?>): List<WorkEntity> {

        return if (list.isNotEmpty()) {
            list.map {
                if (it?.anime != null) {
                    WorkEntity(
                        anime = AnimePosterResponseMapper.toAnimePosterEntity(it.anime),
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


    private fun toSeyuWorks(list: List<SeyuWorksResponse>): List<SeyuWorks> {
        return if (list.isNotEmpty()) {
            list.map { SeyuWorks(characters = it.characters) }
        } else {
            emptyList()
        }
    }
}

