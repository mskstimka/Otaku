package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class GetUserStatsUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(id: Long) = userRepository.getUserStats(id = id)
}