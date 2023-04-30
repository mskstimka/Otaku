package com.example.domain.usecases.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserBriefUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(userAgent: String, accessToken: String) =
        userRepository.getCurrentUser(userAgent = userAgent, accessToken = accessToken)
}