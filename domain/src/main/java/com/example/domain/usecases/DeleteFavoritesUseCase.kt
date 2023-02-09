package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class DeleteFavoritesUseCase(private val repository: AnimeRepository) {
    suspend fun execute(id: Int) {
        return repository.deleteLocalFavorites(id = id)
    }
}