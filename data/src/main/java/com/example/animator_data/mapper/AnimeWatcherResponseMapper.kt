package com.example.animator_data.mapper

import com.example.animator_data.network.models.TranslationResponse
import com.example.animator_domain.models.details.Translation

object AnimeWatcherResponseMapper {

    fun convertResponse(it: TranslationResponse): Translation {
        return Translation(
            id = it.id,
            _kind = it._kind,
            targetId = it.targetId,
            episode = it.episode,
            url = it.url,
            hosting = it.hosting,
            language = it.language,
            author = it.author,
            _quality = it._quality,
            episodesTotal = it.episodesTotal
        )
    }
}