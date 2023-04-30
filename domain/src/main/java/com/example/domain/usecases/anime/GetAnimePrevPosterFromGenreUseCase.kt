package com.example.domain.usecases.anime

import com.example.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimePrevPosterFromGenreUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(genreId: Int) = repository.getGenrePosters(genreId = genreId)

}