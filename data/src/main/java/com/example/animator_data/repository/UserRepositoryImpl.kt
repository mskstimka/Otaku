package com.example.animator_data.repository

import com.example.animator_data.mapper.*
import com.example.animator_data.network.api.UserApi
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.USER_AGENT
import com.example.domain.common.Results
import com.example.domain.models.user.FavoriteList
import com.example.domain.models.user.Rate
import com.example.domain.models.user.UserBrief
import com.example.domain.models.user.UserDetails
import com.example.domain.models.user.history.UserHistory
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.delay

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val authRepository: AuthRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : UserRepository {

    override suspend fun getCurrentUser(
        userAgent: String,
        accessToken: String
    ): Results<UserBrief> {

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
                val refreshToken = sharedPreferencesHelper.getLocalToken()?.refreshToken

                return if (response.code() == 429) {
                    getCurrentUser(userAgent, "Bearer $accessToken")
                } else if (response.code() == 401 && refreshToken != null) {
                    when (val newToken = authRepository.refreshToken(refreshToken)) {
                        is Results.Success -> getCurrentUser(
                            USER_AGENT,
                            "Bearer ${newToken.data.authToken}"
                        )
                        is Results.Error -> Results.Error(exception = Exception(response.message()))
                    }
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }

    }

    override suspend fun getUserBriefInfo(id: Long): Results<UserBrief> {
        return try {
            val response = userApi.getUserBriefInfo(id = id)
            return if (response.isSuccessful) {
                val userBriefResponse = response.body()

                when (userBriefResponse) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(userBriefResponse.toUserBrief())
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(500)
                    getUserBriefInfo(id = id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun getUserFavourites(id: Long): Results<FavoriteList> {
        return try {
            val response = userApi.getUserFavourites(id = id)
            return if (response.isSuccessful) {

                when (val favoriteListResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(favoriteListResponse.toFavoriteList())
                    }
                }
            } else {
                if (response.code() == 429) {
                    getUserFavourites(id = id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun getUserStats(id: Long): Results<UserDetails> {
        return try {
            val response = userApi.getUserStats(id = id)
            return if (response.isSuccessful) {

                when (val favoriteListResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(favoriteListResponse.toUserDetails())
                    }
                }
            } else {
                if (response.code() == 429) {
                    getUserStats(id = id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun getUserFriends(id: Long): Results<List<UserBrief>> {
        return try {
            val response = userApi.getUserFriends(id = id)
            return if (response.isSuccessful) {
                val userBriefResponse = response.body()

                when (userBriefResponse) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(userBriefResponse.toUserBriefList())
                    }
                }
            } else {
                if (response.code() == 429) {
                    getUserFriends(id = id)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun getUserAnimeRates(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ): Results<List<Rate>> {
        return try {
            val response = userApi.getUserAnimeRates(
                id = id,
                page = page,
                limit = limit,
                status = status
            )

            return if (response.isSuccessful) {
                when (val animeRatesResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(animeRatesResponse.toListRates())
                    }
                }
            } else {
                if (response.code() == 429) {
                    getUserAnimeRates(
                        id = id,
                        page = page,
                        limit = limit,
                        status = status
                    )
                } else {
                    Results.Error(exception = Exception(response.message()))
                }

            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun getUserHistory(
        id: Long,
        page: Int,
        limit: Int
    ): Results<List<UserHistory>> {
        return try {
            val response = userApi.getUserHistory(
                id = id,
                page = page,
                limit = limit
            )

            return if (response.isSuccessful) {
                when (val userHistoryResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(userHistoryResponse.toListUserHistory())
                    }
                }
            } else {
                if (response.code() == 429) {
                    getUserHistory(
                        id = id,
                        page = page,
                        limit = limit,
                    )
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }
}

