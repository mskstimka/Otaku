package com.example.domain.repository

import com.example.domain.common.Results
import com.example.domain.models.user.UserBrief

interface UserRepository {

    suspend fun getCurrentUser(
        userAgent: String,
        authHeader: String
    ): Results<UserBrief>

}