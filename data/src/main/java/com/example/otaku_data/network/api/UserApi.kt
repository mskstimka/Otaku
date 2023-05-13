package com.example.otaku_data.network.api

import com.example.otaku_data.network.models.user.*
import com.example.otaku_data.network.models.user.status.UserRateCreateOrUpdateRequest
import com.example.otaku_data.network.models.user.status.UserRateResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("/api/users/whoami")
    suspend fun getCurrentUserBrief(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String
    ): Response<UserBriefResponse>

    @GET("/api/users/{id}")
    suspend fun getUserBriefInfo(@Path("id") id: Long): Response<UserBriefResponse>

    @GET("/api/users/{id}")
    suspend fun getUserBriefInfoWithAuthorization(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<UserBriefResponse>

    @GET("/api/users/{id}/favourites")
    suspend fun getUserFavourites(@Path("id") id: Long): Response<FavoriteListResponse>

    @GET("/api/users/{id}/friends")
    suspend fun getUserFriends(@Path("id") id: Long): Response<List<UserBriefResponse>>

    @GET("/api/users/{id}/history")
    suspend fun getUserHistory(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<UserHistoryResponse>>

    @GET("/api/users/{id}")
    suspend fun getUserStats(@Path("id") id: Long): Response<UserDetailsResponse>

    @GET("/api/users/{id}/anime_rates")
    suspend fun getUserAnimeRates(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("status") status: String
    ): Response<List<RateResponse>>

    @PATCH("/api/v2/user_rates/{id}")
    suspend fun updateAnimeStatus(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long,
        @Body request: UserRateCreateOrUpdateRequest
    ): Response<UserRateResponse>


    @POST("/api/v2/user_rates")
    suspend fun createUserRate(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String,
        @Query("user_id") userId: Long,
        @Body request: UserRateCreateOrUpdateRequest
    ): Response<UserRateResponse>

    @POST("/api/friends/{id}")
    suspend fun addToFriends(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<UserNoticeResponse>

    @DELETE("/api/friends/{id}")
    suspend fun deleteFriend(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<UserNoticeResponse>

    @DELETE("/api/v2/user_rates/{id}")
    suspend fun deleteRate(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Response<Unit>

    @GET("/api/users/sign_out")
    suspend fun signOut(
        @Header("User-Agent") userAgent: String,
        @Header("Authorization") authHeader: String
    ): Response<Unit>
}