package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetAnimePostersFromSearchUseCase(private val repository: AnimeRepository) {

    suspend fun execute(searchName: String) =
        repository.getSearchPosters(searchName = searchName)

}