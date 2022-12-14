package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetAnimePostersFromSearchUseCase(private val repository: AnimeRepository) {

    suspend fun execute(searchName: String) =
        repository.getSearchPosters(searchName = searchName)

}