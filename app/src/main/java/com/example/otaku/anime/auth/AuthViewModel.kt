package com.example.otaku.anime.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.USER_AGENT
import com.example.domain.common.Results
import com.example.domain.models.user.UserBrief
import com.example.domain.usecases.auth.GetAccessTokenUseCase
import com.example.domain.usecases.user.GetCurrentUserBriefUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getCurrentUserBriefUseCase: GetCurrentUserBriefUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionAuth = MutableSharedFlow<Boolean>(replay = 1)
    val actionAuth: SharedFlow<Boolean> get() = _actionAuth

    private val _actionUserBrief = MutableSharedFlow<UserBrief>(replay = 1)
    val actionUserBrief: SharedFlow<UserBrief> get() = _actionUserBrief

    var isAuthorized = false
    var currentId: Long? = null
    val localToken = sharedPreferencesHelper.getLocalToken()

    init {
        if (localToken != null){
            getCurrentUser(localToken.authToken)
        }
    }
    fun getCurrentUser(accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val user =
                getCurrentUserBriefUseCase.execute(
                    userAgent = USER_AGENT,
                    accessToken = accessToken
                )
            when (user) {
                is Results.Success -> {
                    isAuthorized = true
                    currentId = user.data.id
                    _actionUserBrief.tryEmit(user.data)
                }
                is Results.Error -> {
                    _actionError.tryEmit(user.exception.message.toString())
                }
            }

        }
    }

    fun signIn(authCode: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val token = getAccessTokenUseCase.execute(authCode = authCode)) {
                is Results.Success -> {
                    getCurrentUser(token.data.authToken)
                    _actionAuth.tryEmit(true)
                }
                is Results.Error -> {
                    _actionError.tryEmit(token.exception.message.toString())
                }
            }

        }
    }

}