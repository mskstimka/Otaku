package com.example.otaku_domain.usecases.auth

import com.example.otaku_domain.repository.AuthRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(authCode: String) = authRepository.signIn(authCode = authCode)
}