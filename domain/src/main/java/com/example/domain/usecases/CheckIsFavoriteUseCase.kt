package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class CheckIsFavoriteUseCase(private val repository: AnimeRepository) {
    fun execute(id: Int) = repository.checkIsFavorite(id = id)
}