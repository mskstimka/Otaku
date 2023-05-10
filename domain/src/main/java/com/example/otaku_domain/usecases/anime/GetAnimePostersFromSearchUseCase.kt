package com.example.otaku_domain.usecases.anime

import com.example.otaku_domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimePostersFromSearchUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(searchName: String) =
        repository.getSearchPosters(searchName = searchName)

}