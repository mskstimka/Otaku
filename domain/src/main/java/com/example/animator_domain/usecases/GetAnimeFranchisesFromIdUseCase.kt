package com.example.animator_domain.usecases

import com.example.animator_domain.repository.AnimeRepository

class GetAnimeFranchisesFromIdUseCase(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) = repository.getAnimeFranchisesFromId(id = id)

}