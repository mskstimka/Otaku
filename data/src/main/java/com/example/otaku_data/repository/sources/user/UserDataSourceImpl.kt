package com.example.otaku_data.repository.sources.user

import com.example.otaku_data.mapper.*
import com.example.otaku_data.network.api.UserApi
import com.example.otaku_data.network.models.user.UserBriefResponse
import com.example.otaku_data.network.models.user.status.UserRateCreateOrUpdateRequest
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.ERROR_WAIT_TIME
import com.example.otaku_domain.USER_AGENT
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.user.FavoriteList
import com.example.otaku_domain.models.user.Rate
import com.example.otaku_domain.models.user.UserBrief
import com.example.otaku_domain.models.user.UserDetails
import com.example.otaku_domain.models.user.history.UserHistory
import com.example.otaku_domain.models.user.status.UserRate
import kotlinx.coroutines.delay
import retrofit2.Response

class UserDataSourceImpl(
    private val userApi: UserApi,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : UserDataSource {
    override suspend fun getCurrentUser(
        userAgent: String,
        accessToken: String
    ): Response<UserBriefResponse> {

        return userApi.getCurrentUserBrief(userAgent = userAgent, authHeader = accessToken)
    }

    override suspend fun getUserBriefInfo(id: Long): Results<UserBrief> {
        return try {
            val token = sharedPreferencesHelper.getLocalToken()
            val response = if (token == null) {
                userApi.getUserBriefInfo(id = id)
            } else {
                userApi.getUserBriefInfoWithAuthorization(
                    id = id,
                    userAgent = USER_AGENT,
                    authHeader = "Bearer ${token.authToken}"
                )
            }
            if (response.isSuccessful) {

                when (val userBriefResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(userBriefResponse.toUserBrief())
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
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
            if (response.isSuccessful) {

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
                    delay(ERROR_WAIT_TIME)
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
            if (response.isSuccessful) {

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
                    delay(ERROR_WAIT_TIME)
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
            if (response.isSuccessful) {
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
                    delay(ERROR_WAIT_TIME)
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

            if (response.isSuccessful) {
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
                    delay(ERROR_WAIT_TIME)
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

            if (response.isSuccessful) {
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
                    delay(ERROR_WAIT_TIME)
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

    override suspend fun updateUserRate(
        userAgent: String,
        authHeader: String,
        rateId: Long,
        userRate: UserRate
    ): Results<UserRate> {
        return try {
            val response = userApi.updateAnimeStatus(
                userAgent = userAgent,
                authHeader = authHeader,
                id = rateId,
                request = UserRateCreateOrUpdateRequest(userRate = userRate.toUserRateResponse())
            )

            if (response.isSuccessful) {
                when (val updateUserRateResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(updateUserRateResponse.toUserRate())
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    updateUserRate(
                        userAgent = userAgent,
                        authHeader = authHeader,
                        rateId = rateId,
                        userRate = userRate
                    )
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun createUserRate(
        userAgent: String,
        authHeader: String,
        userId: Long,
        userRate: UserRate
    ): Results<UserRate> {
        return try {
            userRate.userId = userId
            val response = userApi.createUserRate(
                userAgent = userAgent,
                authHeader = authHeader,
                userId = userId,
                request = UserRateCreateOrUpdateRequest(userRate = userRate.toUserRateResponse())
            )
            if (response.isSuccessful) {
                when (val updateUserRateResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(updateUserRateResponse.toUserRate())
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    createUserRate(
                        userAgent = userAgent,
                        authHeader = authHeader,
                        userId = userId,
                        userRate = userRate
                    )
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }

    }

    override suspend fun addToFriends(
        userAgent: String,
        authHeader: String,
        id: Long
    ): Results<String> {
        return try {
            val response = userApi.addToFriends(
                id = id,
                userAgent = userAgent,
                authHeader = authHeader
            )

            if (response.isSuccessful) {
                when (val response = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(response.notice.toString())
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    addToFriends(id = id, userAgent = userAgent, authHeader = authHeader)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun deleteFriend(
        userAgent: String,
        authHeader: String,
        id: Long
    ): Results<String> {
        return try {
            val response = userApi.deleteFriend(
                id = id,
                userAgent = userAgent,
                authHeader = authHeader
            )

            if (response.isSuccessful) {
                when (val response = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success(response.notice.toString())
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    deleteFriend(id = id, userAgent = userAgent, authHeader = authHeader)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }

            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun deleteRate(
        userAgent: String,
        authHeader: String,
        id: Long
    ): Results<String> {
        return try {
            val response =
                userApi.deleteRate(id = id, userAgent = userAgent, authHeader = authHeader)

            if (response.isSuccessful) {
                Results.Success("Deleted Rate")
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    deleteRate(
                        id = id,
                        userAgent = userAgent,
                        authHeader = authHeader
                    )
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }

    override suspend fun signOut(userAgent: String, authHeader: String): Results<String> {
        return try {
            val response = userApi.signOut(
                userAgent = userAgent,
                authHeader = authHeader
            )

            if (response.isSuccessful) {
                when (response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        Results.Success("Signed Out")
                    }
                }
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    signOut(userAgent = userAgent, authHeader = authHeader)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }

            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }
}
