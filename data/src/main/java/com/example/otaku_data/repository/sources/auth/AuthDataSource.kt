package com.example.otaku_data.repository.sources.auth

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.Token

interface AuthDataSource {

    suspend fun signIn(authCode: String): Results<Token>

    suspend fun refreshToken(refreshToken: String): Results<Token>
}