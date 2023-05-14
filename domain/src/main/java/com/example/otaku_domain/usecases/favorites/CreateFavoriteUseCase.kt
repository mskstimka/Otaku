package com.example.otaku_domain.usecases.favorites

import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class CreateFavoriteUseCase @Inject constructor(private val animeRepository: AnimeRepository) {

    suspend fun execute(linkedId: Long, linkedType: String) =
        animeRepository.createFavorite(linkedId, linkedType = linkedType)
}