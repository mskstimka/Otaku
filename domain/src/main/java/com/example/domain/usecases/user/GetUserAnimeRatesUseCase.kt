package com.example.domain.usecases.user

import com.example.domain.common.Results
import com.example.domain.models.user.Rate
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserAnimeRatesUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ) = userRepository.getUserAnimeRates(id = id, page = page, limit = limit, status = status)
}