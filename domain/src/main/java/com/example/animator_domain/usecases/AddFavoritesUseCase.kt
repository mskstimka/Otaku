package com.example.animator_domain.usecases

import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator_domain.repository.AnimeRepository

class AddFavoritesUseCase(private val repository: AnimeRepository) {

    suspend fun execute(item: AnimePosterEntity) {
        repository.addLocalFavorites(item = item)
    }
}