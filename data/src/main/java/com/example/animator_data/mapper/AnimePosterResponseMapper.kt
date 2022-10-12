package com.example.animator_data.mapper

import com.example.animator_data.network.models.AnimePosterEntityResponse
import com.example.animator_domain.*
import com.example.animator_domain.models.poster.AnimePosterEntity

object AnimePosterResponseMapper {

    fun toListAnimePosterEntity(list: List<AnimePosterEntityResponse>): List<AnimePosterEntity> {
        return list.map {
            AnimePosterEntity(
                id = it.id,
                image = it.image,
                name = it.name,
                score = it.score,
                episodes = it.episodes,
                episodesAired = it.episodesAired,
                url = it.url,
                status = it.status,
                statusColor = checkStatusColor(it.status),
                russian = it.russian
            )
        }
    }

    private fun checkStatusColor(status: String?): String = when (status) {
        ONGOING_STATUS -> BLUE_STATUS_COLOR
        ANONS_STATUS -> RED_STATUS_COLOR
        else -> GREEN_STATUS_COLOR
    }

    fun toAnimePosterEntity(item: AnimePosterEntityResponse): AnimePosterEntity {
        return AnimePosterEntity(
            id = item.id,
            image = item.image,
            name = item.name,
            score = item.score,
            episodes = item.episodes,
            episodesAired = item.episodesAired,
            url = item.url,
            status = item.status,
            statusColor = checkStatusColor(item.status),
            russian = item.russian
        )
    }
}
