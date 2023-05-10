package com.example.otaku_data.repository

import com.example.otaku_data.repository.sources.auth.AuthDataSource
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.Token
import com.example.otaku_domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signIn(authCode: String): Results<Token> {
        return authDataSource.signIn(authCode = authCode)
    }

    override suspend fun refreshToken(refreshToken: String): Results<Token> {
        return authDataSource.refreshToken(refreshToken = refreshToken)
    }

}
