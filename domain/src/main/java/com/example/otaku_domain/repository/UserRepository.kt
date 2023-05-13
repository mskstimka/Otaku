package com.example.otaku_domain.repository

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.user.FavoriteList
import com.example.otaku_domain.models.user.Rate
import com.example.otaku_domain.models.user.UserBrief
import com.example.otaku_domain.models.user.UserDetails
import com.example.otaku_domain.models.user.history.UserHistory
import com.example.otaku_domain.models.user.status.UserRate

interface UserRepository {

    suspend fun getCurrentUser(
        userAgent: String,
        accessToken: String
    ): Results<UserBrief>

    suspend fun getUserBriefInfo(id: Long): Results<UserBrief>
    suspend fun getUserFavourites(id: Long): Results<FavoriteList>
    suspend fun getUserStats(id: Long): Results<UserDetails>
    suspend fun getUserFriends(id: Long): Results<List<UserBrief>>

    suspend fun getUserAnimeRates(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ): Results<List<Rate>>

    suspend fun getUserHistory(id: Long, page: Int, limit: Int): Results<List<UserHistory>>

    suspend fun updateUserRate(
        userAgent: String,
        authHeader: String,
        rateId: Long,
        userRate: UserRate
    ): Results<UserRate>

    suspend fun createUserRate(
        userAgent: String,
        authHeader: String,
        userId: Long,
        userRate: UserRate
    ): Results<UserRate>

    suspend fun addToFriends(id: Long): Results<String>

    suspend fun deleteFriend(id: Long): Results<String>

    suspend fun deleteRate(id: Long): Results<String>

    suspend fun signOut(): Results<String>
}