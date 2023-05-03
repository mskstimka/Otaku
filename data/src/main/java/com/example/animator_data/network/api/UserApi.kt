package com.example.animator_data.network.api

import com.example.animator_data.network.models.user.FavoriteListResponse
import com.example.animator_data.network.models.user.RateResponse
import com.example.animator_data.network.models.user.UserBriefResponse
import com.example.animator_data.network.models.user.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("/api/users/whoami")
    suspend fun getCurrentUserBrief(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String
    ): Response<UserBriefResponse>

    @GET("/api/users/{id}/info")
    suspend fun getUserBriefInfo(@Path("id") id: Long): Response<UserBriefResponse>
    @GET("/api/users/{id}/favourites")
    suspend fun getUserFavourites(@Path("id") id: Long): Response<FavoriteListResponse>

    @GET("/api/users/{id}")
    suspend fun getUserStats(@Path("id") id: Long): Response<UserDetailsResponse>

    @GET("/api/users/{id}/anime_rates")
    suspend fun getUserAnimeRates(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("status") status: String
    ): Response<List<RateResponse>>
}