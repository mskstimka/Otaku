package com.example.a16_rxjava_data.mapper

import com.example.a16_rxjava_data.network.models.*
import com.example.a16_rxjava_domain.*
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.Genre
import com.example.a16_rxjava_domain.models.details.Studio
import com.example.a16_rxjava_domain.models.details.Video
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import java.util.*

object AnimeDetailsResponseMapper {


    private val defaultVideo = Video(
        hosting = NOT_FOUND_TEXT, id = 0, image_url = "http", kind = NOT_FOUND_TEXT,
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
            url = NOT_FOUND_TEXT, weight = 404, year = 404
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
                        weight = it.weight,
                        year = it.year
                    )
                )
            }

        }
        return list
    }


    fun toAnimeRolesEntity(item: AnimeDetailsRolesEntityResponse) = AnimeDetailsRolesEntity(
        character = item.character,
        person = item.person,
        roles = item.roles,
        roles_russian = item.roles_russian,
        id = UUID.randomUUID().toString()
    )

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
}