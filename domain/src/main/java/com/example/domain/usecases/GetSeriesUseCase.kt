package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetSeriesUseCase(private val repository: AnimeRepository) {

    suspend fun execute(malId: Long, name: String) = repository.getSeries(malId, name)

}