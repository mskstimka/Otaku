package com.example.animator_data.repository

import com.example.animator_data.mapper.toToken
import com.example.animator_data.network.api.AuthApi
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.ERROR_WAIT_TIME
import com.example.domain.common.Results
import com.example.domain.models.Token
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : AuthRepository {

    override suspend fun signIn(authCode: String): Results<Token> {
        return try {
            val response = authApi.getAccessToken(grantType = AUTH_CODE, code = authCode)
            if (response.isSuccessful) {
                val item = response.body()!!.toToken()
                sharedPreferencesHelper.setLocalToken(item)
                Results.Success(data = item)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }


    override suspend fun refreshToken(refreshToken: String): Results<Token> {
        return try {
            val response = authApi.getAccessToken(
                grantType = REFRESH_TOKEN,
                refreshToken = refreshToken,
                code = refreshToken
            )
            if (response.isSuccessful) {
                val item = response.body()!!.toToken()
                sharedPreferencesHelper.setLocalToken(item)
                Results.Success(data = item)
            } else {
                if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    refreshToken(refreshToken)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    companion object {
        private const val AUTH_CODE = "authorization_code"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}
