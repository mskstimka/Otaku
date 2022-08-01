package com.example.a16_rxjava_domain.usecases

import com.example.a16_rxjava_domain.repository.AnimeRepository

class GetAnimePostersFromSearchUseCase(private val repository: AnimeRepository) {

   fun execute(searchName: String) =
        repository.getAnimePostersFromSearch(searchName)

}