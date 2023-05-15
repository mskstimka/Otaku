package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class GetUserWatchingAnimeRatesUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ) = userRepository.getUserAnimeRates(id = id, page = page, limit = limit, status = status)
}