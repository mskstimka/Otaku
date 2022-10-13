package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetFavoritesUseCase(private val repository: AnimeRepository) {

    fun execute() = repository.getLocalFavorites()
}