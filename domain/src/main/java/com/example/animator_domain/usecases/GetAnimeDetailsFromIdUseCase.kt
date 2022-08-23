package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetAnimeDetailsFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getAnimeDetailsFromId(id = id)

}