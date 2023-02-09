package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetAnimeScreenshotsFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) =
        repository.getScreenshots(id = id)

}