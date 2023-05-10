package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.models.user.status.UserRate
import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class CreateUserRateUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(
        userAgent: String,
        authHeader: String,
        userId: Long,
        userRate: UserRate
    ) = userRepository.createUserRate(
        userAgent = userAgent,
        authHeader = authHeader,
        userId = userId,
        userRate = userRate
    )
}