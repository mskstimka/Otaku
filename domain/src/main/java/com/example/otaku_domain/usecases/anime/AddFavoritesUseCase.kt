package com.example.otaku_domain.usecases.anime

import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class AddFavoritesUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(item: AnimePosterEntity) = repository.addLocalFavorites(item = item)

}