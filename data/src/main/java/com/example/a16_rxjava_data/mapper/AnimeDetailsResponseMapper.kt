package com.example.a16_rxjava_data.mapper


import com.example.a16_rxjava_data.models.*
import com.example.a16_rxjava_domain.models.Constants
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.Genre
import com.example.a16_rxjava_domain.models.details.Studio
import com.example.a16_rxjava_domain.models.details.Video
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.roles.Character
import com.example.a16_rxjava_domain.models.details.roles.Image
import com.example.a16_rxjava_domain.models.details.roles.Person
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import java.util.*

object AnimeDetailsResponseMapper {


    private const val message = Constants.NOT_FOUND_TEXT
    private val defaultVideo = Video(message, 0, "http", message, message, message, message)
    private val defaultStudio = Studio(message, 404, message, message, false)
    private val defaultGenre = Genre(404, message, message, message)
    private val defaultScreenshot = AnimeDetailsScreenshotsEntity(message, message)
    private val defaultFranchise =
        AnimeDetailsFranchisesEntity(404, 0, "x96", message, message, message, 404, 404)
    private val defaultCharacter =
        Character(404, Image(message, message, message, message), message, message, message)
    private val defaultPerson =
        Person(404, Image(message, message, message, message), message, message, message)


    fun toAnimeDetailsEntity(item: AnimeDetailsEntityResponse) = AnimeDetailsEntity(
        item.aired_on,
        item.anons,
        item.description,
        item.description_html,
        item.description_source,
        item.duration,
        item.english,
        item.episodes,
        item.episodes_aired,
        item.fandubbers,
        item.fansubbers,
        item.favoured,
        item.franchise,
        checkGenresAdapter(item.genres),
        item.id,
        item.image,
        item.japanese,
        item.kind,
        item.license_name_ru,
        item.licensors,
        item.myanimelist_id,
        item.name,
        item.next_episode_at,
        item.ongoing,
        item.rates_scores_stats,
        item.rates_statuses_stats,
        item.rating,
        item.released_on,
        item.russian,
        item.score,
        item.screenshots,
        item.status,
        checkStudioList(item.studios),
        item.synonyms,
        item.thread_id,
        item.topic_id,
        item.updated_at,
        item.url,
        item.user_rate,
        checkVideoList(item.videos)
    )

    fun toAnimeScreenshotsEntity(list: List<AnimeDetailsScreenshotsEntityResponse>): List<AnimeDetailsScreenshotsEntity> {

        var screenshots: MutableList<AnimeDetailsScreenshotsEntity> = mutableListOf()

        when (list) {
            emptyList<AnimeDetailsScreenshotsEntityResponse>() -> {
                screenshots = mutableListOf(defaultScreenshot)
            }
            else -> list.forEach {
                screenshots.add(
                    AnimeDetailsScreenshotsEntity(
                        it.original,
                        it.preview
                    )
                )
            }
        }
        return screenshots
    }

    fun toListAnimeFranchisesEntity(item: AnimeDetailsFranchisesEntityResponse): List<AnimeDetailsFranchisesEntity> {

        var list: MutableList<AnimeDetailsFranchisesEntity> = mutableListOf()

        when (item.nodes) {
            emptyList<Node>() -> item.nodes.forEach {
                list.add(
                    AnimeDetailsFranchisesEntity(
                        it.date,
                        it.id,
                        it.image_url,
                        it.kind,
                        it.name,
                        it.image_url,
                        it.weight,
                        it.year
                    )
                )
            }
            else -> list = mutableListOf(defaultFranchise)
        }
        return list
    }


    fun toAnimeRolesEntity(item: AnimeDetailsRolesEntityResponse) = AnimeDetailsRolesEntity(
        item.character,
        item.person,
        item.roles,
        item.roles_russian,
        UUID.randomUUID().toString()
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