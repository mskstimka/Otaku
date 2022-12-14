package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetAnimeScreenshotsFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) =
        repository.getScreenshots(id = id)

}