package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetCharacterDetailsUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) =
        repository.getCharacters(id = id)
}