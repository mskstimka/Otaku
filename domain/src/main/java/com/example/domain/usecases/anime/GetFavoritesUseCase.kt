package com.example.domain.usecases.anime

import com.example.domain.repository.AnimeRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: AnimeRepository) {

    fun execute() = repository.getLocalFavorites()
}