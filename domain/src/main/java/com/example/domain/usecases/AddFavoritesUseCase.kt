package com.example.domain.usecases

import com.example.domain.models.poster.AnimePosterEntity
import com.example.domain.repository.AnimeRepository

class AddFavoritesUseCase(private val repository: AnimeRepository) {

    suspend fun execute(item: AnimePosterEntity) {
        repository.addLocalFavorites(item = item)
    }
}