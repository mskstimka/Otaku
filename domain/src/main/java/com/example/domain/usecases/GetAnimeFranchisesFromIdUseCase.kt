package com.example.domain.usecases

import com.example.domain.repository.AnimeRepository

class GetAnimeFranchisesFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getFranchises(id = id)

}