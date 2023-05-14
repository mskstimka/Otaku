package com.example.otaku_data.repository

import android.util.Log
import com.example.otaku_data.mapper.toUserBrief
import com.example.otaku_data.repository.sources.auth.AuthDataSource
import com.example.otaku_data.repository.sources.user.UserDataSource
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.ERROR_WAIT_TIME
import com.example.otaku_domain.NO_CURRENT_USER_ID
import com.example.otaku_domain.USER_AGENT
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.user.FavoriteList
import com.example.otaku_domain.models.user.Rate
import com.example.otaku_domain.models.user.UserBrief
import com.example.otaku_domain.models.user.UserDetails
import com.example.otaku_domain.models.user.history.UserHistory
import com.example.otaku_domain.models.user.status.UserRate
import com.example.otaku_domain.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val authDataSource: AuthDataSource,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : UserRepository {
    override suspend fun getCurrentUser(
        userAgent: String,
        accessToken: String
    ): Results<UserBrief> {

        return try {
            val response = userDataSource.getCurrentUser(userAgent, "Bearer $accessToken")
            return if (response.isSuccessful) {

                when (val userResponse = response.body()) {
                    null -> {
                        Results.Error(Exception("Token Error Response"))
                    }
                    else -> {
                        sharedPreferencesHelper.setCurrentUserId(userResponse.id)
                        Results.Success(userResponse.toUserBrief())
                    }
                }
            } else {
                val refreshToken = sharedPreferencesHelper.getLocalToken()?.refreshToken

                return if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    getCurrentUser(userAgent, "Bearer $accessToken")
                } else if (response.code() == 401 && refreshToken != null) {
                    when (val newToken = authDataSource.refreshToken(refreshToken)) {
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
        return userDataSource.getUserBriefInfo(id = id)
    }

    override suspend fun getUserFavourites(id: Long): Results<FavoriteList> {
        return userDataSource.getUserFavourites(id = id)
    }

    override suspend fun getUserStats(id: Long): Results<UserDetails> {
        return userDataSource.getUserStats(id = id)
    }

    override suspend fun getUserFriends(id: Long): Results<List<UserBrief>> {
        return userDataSource.getUserFriends(id = id)
    }

    override suspend fun getUserAnimeRates(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ): Results<List<Rate>> {
        return userDataSource.getUserAnimeRates(
            id = id,
            page = page,
            limit = limit,
            status = status
        )
    }

    override suspend fun getUserHistory(
        id: Long,
        page: Int,
        limit: Int
    ): Results<List<UserHistory>> {
        return userDataSource.getUserHistory(id = id, page = page, limit = limit)
    }

    override suspend fun updateUserRate(
        userAgent: String,
        authHeader: String,
        rateId: Long,
        userRate: UserRate
    ): Results<UserRate> {
        return userDataSource.updateUserRate(
            userAgent = userAgent,
            authHeader = authHeader,
            rateId = rateId,
            userRate = userRate
        )
    }

    override suspend fun createUserRate(
        userAgent: String,
        authHeader: String,
        userId: Long,
        userRate: UserRate
    ): Results<UserRate> {
        return userDataSource.createUserRate(
            userAgent = userAgent,
            authHeader = authHeader,
            userId = userId,
            userRate = userRate
        )
    }

    override suspend fun addToFriends(id: Long): Results<String> {
        return userDataSource.addToFriends(
            id = id,
            userAgent = USER_AGENT,
            authHeader = "Bearer ${sharedPreferencesHelper.getLocalToken()?.authToken.toString()}"
        )
    }

    override suspend fun deleteFriend(id: Long): Results<String> {
        return userDataSource.deleteFriend(
            id = id,
            userAgent = USER_AGENT,
            authHeader = "Bearer ${sharedPreferencesHelper.getLocalToken()?.authToken.toString()}"
        )
    }

    override suspend fun deleteRate(id: Long): Results<String> {
        return userDataSource.deleteRate(
            id = id,
            userAgent = USER_AGENT,
            authHeader = "Bearer ${sharedPreferencesHelper.getLocalToken()?.authToken.toString()}"
        )
    }

    override suspend fun signOut(): Results<String> {
        val result = userDataSource.signOut(
            userAgent = USER_AGENT,
            authHeader = "Bearer ${sharedPreferencesHelper.getLocalToken()?.authToken.toString()}"
        )
        if (result is Results.Success) {
            sharedPreferencesHelper.removeLocalToken()
            sharedPreferencesHelper.setCurrentUserId(NO_CURRENT_USER_ID)
        } else {
            Log.d("ERROR AUTH", result.toString())
        }
        return result
    }

}



