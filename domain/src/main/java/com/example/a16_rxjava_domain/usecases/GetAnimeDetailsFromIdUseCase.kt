package com.example.a16_rxjava_domain.usecases

import com.example.a16_rxjava_domain.repository.AnimeRepository

class GetAnimeDetailsFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getAnimeDetailsFromId(id)

}