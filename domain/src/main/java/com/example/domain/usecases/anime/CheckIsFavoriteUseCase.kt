package com.example.domain.usecases.anime

import com.example.domain.repository.AnimeRepository
import javax.inject.Inject

class CheckIsFavoriteUseCase @Inject constructor(private val repository: AnimeRepository) {
    fun execute(id: Int) = repository.checkIsFavorite(id = id)
}