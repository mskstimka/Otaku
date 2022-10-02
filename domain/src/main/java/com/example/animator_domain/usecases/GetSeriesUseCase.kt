package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetSeriesUseCase(private val repository: AnimeRepository) {

    suspend fun execute(malId: Long, name: String) = repository.getSeries(malId, name)

}