package com.example.domain.usecases.anime

import com.example.domain.repository.AnimeRepository
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(private val repository: AnimeRepository) {

    suspend fun execute(id: Int) =
        repository.getCharacters(id = id)
}