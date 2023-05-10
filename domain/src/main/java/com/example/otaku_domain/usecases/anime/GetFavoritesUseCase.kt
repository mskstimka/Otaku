package com.example.otaku_domain.usecases.anime

import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: AnimeRepository) {

    fun execute() = repository.getLocalFavorites()
}