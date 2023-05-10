package com.example.otaku_domain.usecases.anime

import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class DeleteFavoritesUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.deleteLocalFavorites(id = id)

}