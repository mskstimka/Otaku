package com.example.domain.usecases.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserHistoryUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(
        id: Long,
        page: Int,
        limit: Int
    ) = userRepository.getUserHistory(id = id, page = page, limit = limit)
}