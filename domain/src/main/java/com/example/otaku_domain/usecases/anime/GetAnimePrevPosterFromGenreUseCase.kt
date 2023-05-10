package com.example.otaku_domain.usecases.anime

import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimePrevPosterFromGenreUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(genreId: Int) = repository.getGenrePosters(genreId = genreId)

}