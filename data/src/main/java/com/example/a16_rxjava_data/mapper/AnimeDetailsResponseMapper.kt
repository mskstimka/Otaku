package com.example.a16_rxjava_data.mapper

import com.example.a16_rxjava_data.models.AnimeDetailsEntityResponse
import com.example.a16_rxjava_data.models.AnimeDetailsFranchisesEntityResponse
import com.example.a16_rxjava_data.models.AnimeDetailsRolesEntityResponse
import com.example.a16_rxjava_data.models.AnimeDetailsScreenshotsEntityResponse
import com.example.a16_rxjava_domain.models.details.AnimeDetailsEntity
import com.example.a16_rxjava_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.a16_rxjava_domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.a16_rxjava_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import java.util.*

object AnimeDetailsResponseMapper {

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
        item.genres,
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
        item.studios,
        item.synonyms,
        item.thread_id,
        item.topic_id,
        item.updated_at,
        item.url,
        item.user_rate,
        item.videos
    )

    fun toAnimeScreenshotsEntity(item: AnimeDetailsScreenshotsEntityResponse) =
        AnimeDetailsScreenshotsEntity(
            item.original,
            item.preview
        )

    fun toAnimeFranchisesEntity(item: AnimeDetailsFranchisesEntityResponse): List<AnimeDetailsFranchisesEntity> {

        val list: MutableList<AnimeDetailsFranchisesEntity> = mutableListOf()
        item.nodes.forEach {
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
        return list
    }

    fun toAnimeRolesEntity(item: AnimeDetailsRolesEntityResponse) = AnimeDetailsRolesEntity(
        item.character,
        item.person,
        item.roles,
        item.roles_russian,
        UUID.randomUUID().toString()
    )

}