package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserBriefUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(userAgent: String, accessToken: String) =
        userRepository.getCurrentUser(userAgent = userAgent, accessToken = accessToken)
}