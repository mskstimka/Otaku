package com.example.otaku_data.network.api

import com.example.otaku_data.network.models.TokenResponse
import com.example.otaku_domain.*
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/oauth/token")
    suspend fun getAccessToken(
        @Header("User-Agent") userAgent: String = USER_AGENT,
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET_ID,
        @Query("code") code: String?,
        @Query("redirect_uri") redirectUri: String? = REDIRECT_URI,
        @Query("refresh_token") refreshToken: String? = null
    ): Response<TokenResponse>


}