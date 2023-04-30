package com.example.animator_data.network.api

import com.example.animator_data.network.models.user.UserBriefResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("/api/users/whoami")
    suspend fun getCurrentUserBrief(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String
    ): Response<UserBriefResponse>
}