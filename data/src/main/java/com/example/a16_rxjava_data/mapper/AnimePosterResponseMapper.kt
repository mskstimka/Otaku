package com.example.a16_rxjava_data.mapper

import com.example.a16_rxjava_data.models.AnimePosterEntityResponse
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity

object AnimePosterResponseMapper {

    fun toListAnimePosterEntity(list: List<AnimePosterEntityResponse>): List<AnimePosterEntity> {
        return list.map {
            AnimePosterEntity(
                it.id,
                it.image,
                it.name,
                it.score,
                it.episodes,
                it.episodesAired,
                it.url,
                it.status
            )
        }
    }

    fun toAnimePosterEntity(item: AnimePosterEntityResponse): AnimePosterEntity {
        return AnimePosterEntity(
            item.id,
            item.image,
            item.name,
            item.score,
            item.episodes,
            item.episodesAired,
            item.url,
            item.status
        )
    }
}