package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetAnimeDetailsFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getDetails(id = id)

}