package com.example.otaku_data.repository.sources.auth

import android.util.Log
import com.example.otaku_data.mapper.toToken
import com.example.otaku_data.network.api.AuthApi
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.ERROR_WAIT_TIME
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.Token
import kotlinx.coroutines.delay
import retrofit2.Response

class AuthDataSourceImpl(
    private val authApi: AuthApi,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : AuthDataSource {

    override suspend fun signIn(authCode: String): Results<Token> {
        return try {
            val response = authApi.getAccessToken(grantType = AUTH_CODE, code = authCode)
            if (response.isSuccessful) {
                val item = response.body()!!.toToken()
                Log.d("TOKEN_AUTH", response.body().toString())
                sharedPreferencesHelper.setLocalToken(item)
                Results.Success(data = item)
            } else {
                return if (response.code() == 429) {
                    delay(ERROR_WAIT_TIME)
                    signIn(authCode)
                } else {
                    Results.Error(exception = Exception(response.message()))
                }
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
                Results.Error(Exception(response.message()))
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