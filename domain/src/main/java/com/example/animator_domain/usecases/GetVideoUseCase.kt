package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetVideoUseCase(private val repository: AnimeRepository) {

    suspend fun execute(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ) = repository.getVideo(malId = malId, episode = episode, name = name, kind = kind)
}