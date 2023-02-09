package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetAnimeRolesFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getRoles(id = id)

}