package com.example.domain.repository

import com.example.domain.common.Results
import com.example.domain.models.Token

interface AuthRepository {


    suspend fun signIn(authCode: String): Results<Token>

    suspend fun refreshToken(refreshToken: String): Results<Token>

}