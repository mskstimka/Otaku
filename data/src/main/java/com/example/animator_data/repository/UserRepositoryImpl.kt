package com.example.animator_data.repository

import com.example.animator_data.mapper.*
import com.example.animator_data.network.api.AuthApi
import com.example.animator_data.network.api.UserApi
import com.example.animator_data.network.models.user.RateResponse
import com.example.animator_data.network.models.user.UserDetailsResponse
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.USER_AGENT
import com.example.domain.common.Results
import com.example.domain.models.user.FavoriteList
import com.example.domain.models.user.Rate
import com.example.domain.models.user.UserBrief
import com.example.domain.models.user.UserDetails
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import retrofit2.http.Query

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
                return Results.Error(exception = Exception(response.message()))
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
                return Results.Error(exception = Exception(response.message()))
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
                return Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(Exception(e.message))
        }
    }
}

