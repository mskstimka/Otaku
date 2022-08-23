package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimePrevPosterFromGenreUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(genreId: Int) = repository.getAnimePrevPostersFromGenres(genreId = genreId)

}