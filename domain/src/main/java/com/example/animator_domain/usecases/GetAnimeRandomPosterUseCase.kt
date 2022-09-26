package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetAnimeRandomPosterUseCase(private val repository: AnimeRepository) {

    suspend fun execute(
        limit: Int = 1,
        censored: Boolean = true,
        order: String = "random"
    ) = repository.getRandomPoster(limit = limit, censored = censored, order = order)

}