package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class CheckIsFavoriteUseCase(private val repository: AnimeRepository) {
    fun execute(id: Int) = repository.checkIsFavorite(id = id)
}