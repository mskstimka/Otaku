package com.example.animator_data.repository

import com.example.animator_data.mapper.toUserBrief
import com.example.animator_data.network.api.UserApi
import com.example.domain.common.Results
import com.example.domain.models.user.UserBrief
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun getCurrentUser(userAgent: String, accessToken: String): Results<UserBrief> {

        return try {
            val response = userApi.getCurrentUserBrief(userAgent, "Bearer $accessToken")
            return if (response.isSuccessful) {

                when (val userResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(userResponse.toUserBrief())
                    }
                }
            } else {
                return Results.Error(exception = Exception(""))
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }

    }
}