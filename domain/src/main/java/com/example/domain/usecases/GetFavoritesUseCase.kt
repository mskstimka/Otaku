package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetFavoritesUseCase(private val repository: AnimeRepository) {

    fun execute() = repository.getLocalFavorites()
}