package com.example.animator_domain.usecases

import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator_domain.repository.AnimeRepository

class DeleteFavoritesUseCase(private val repository: AnimeRepository) {
    suspend fun execute(id: Int) {
        return repository.deleteLocalFavorites(id = id)
    }
}