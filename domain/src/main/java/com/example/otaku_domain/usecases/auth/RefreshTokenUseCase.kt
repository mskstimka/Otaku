package com.example.otaku_domain.usecases.auth

import com.example.otaku_domain.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(token: com.example.otaku_domain.models.Token) =
        authRepository.refreshToken(token.refreshToken)
}