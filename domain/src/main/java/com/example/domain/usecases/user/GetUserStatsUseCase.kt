package com.example.domain.usecases.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserStatsUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(id: Long) = userRepository.getUserStats(id = id)
}