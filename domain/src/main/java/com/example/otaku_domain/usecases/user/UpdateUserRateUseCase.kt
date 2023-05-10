package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.models.user.status.UserRate
import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserRateUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(
        userAgent: String,
        authHeader: String,
        rateId: Long,
        userRate: UserRate
    ) = userRepository.updateUserRate(
        userAgent = userAgent,
        authHeader = authHeader,
        rateId = rateId,
        userRate = userRate
    )
}