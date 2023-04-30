package com.example.domain.usecases.anime

import com.example.domain.models.poster.AnimePosterEntity
import com.example.domain.repository.AnimeRepository
import javax.inject.Inject

class AddFavoritesUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(item: AnimePosterEntity) {
        repository.addLocalFavorites(item = item)
    }
}