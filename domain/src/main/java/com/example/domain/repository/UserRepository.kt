package com.example.domain.repository

import com.example.domain.common.Results
import com.example.domain.models.user.FavoriteList
import com.example.domain.models.user.Rate
import com.example.domain.models.user.UserBrief
import com.example.domain.models.user.UserDetails

interface UserRepository {

    suspend fun getCurrentUser(
        userAgent: String,
        accessToken: String
    ): Results<UserBrief>

    suspend fun getUserFavourites(id: Long): Results<FavoriteList>

    suspend fun getUserStats(id: Long): Results<UserDetails>
    suspend fun getUserAnimeRates(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ): Results<List<Rate>>
}