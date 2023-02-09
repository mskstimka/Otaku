package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetPersonUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getPersons(id = id)
}