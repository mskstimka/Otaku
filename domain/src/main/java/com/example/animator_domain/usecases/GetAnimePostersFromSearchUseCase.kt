package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetAnimePostersFromSearchUseCase(private val repository: AnimeRepository) {

   fun execute(searchName: String) =
        repository.getAnimePostersFromSearch(searchName = searchName)

}