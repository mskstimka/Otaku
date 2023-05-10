package com.example.otaku_domain.usecases.anime

import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(malId: Long, name: String) = repository.getSeries(malId, name)

}