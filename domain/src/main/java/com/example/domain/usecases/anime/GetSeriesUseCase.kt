package com.example.domain.usecases.anime

import com.example.domain.repository.AnimeRepository
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(malId: Long, name: String) = repository.getSeries(malId, name)

}