package com.example.otaku.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.USER_AGENT
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.user.UserBrief
import com.example.otaku_domain.usecases.auth.GetAccessTokenUseCase
import com.example.otaku_domain.usecases.user.GetCurrentUserBriefUseCase
import com.example.otaku_domain.usecases.user.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getCurrentUserBriefUseCase: GetCurrentUserBriefUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    val localToken = sharedPreferencesHelper.getLocalToken()

    val isAuth =
        if (localToken == null) AuthAction.IS_UNAUTHORIZED else AuthAction.IS_AUTHORIZED(token = localToken)

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionAuth = MutableStateFlow(isAuth)
    val actionAuth: StateFlow<AuthAction> get() = _actionAuth

    private val _actionUserBrief = MutableSharedFlow<UserBrief>(replay = 1)
    val actionUserBrief: SharedFlow<UserBrief> get() = _actionUserBrief

    var isAuthorized = false
    var currentId: Long? = null

    fun checkResult() {
        viewModelScope.launch {
            val token = sharedPreferencesHelper.getLocalToken()
            if (token != null) {
                _actionAuth.tryEmit(AuthAction.IS_AUTHORIZED(token))
            } else {
                _actionAuth.tryEmit(AuthAction.IS_UNAUTHORIZED)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = signOutUseCase.execute()) {
                is Results.Success -> {
                    _actionError.tryEmit(result.data)
                    _actionAuth.tryEmit(AuthAction.IS_UNAUTHORIZED)
                }
                is Results.Error -> {
                    _actionError.tryEmit(result.exception.message.toString())
                }
            }
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
                    _actionAuth.tryEmit(AuthAction.ERROR)
                }
            }

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clearErrors() {
        viewModelScope.launch(Dispatchers.IO) {
            _actionError.resetReplayCache()
        }
    }

    fun signIn(authCode: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val token = getAccessTokenUseCase.execute(authCode = authCode)) {
                is Results.Success -> {
                    getCurrentUser(token.data.authToken)
                    _actionAuth.tryEmit(AuthAction.ACTIVITY_ON_BACK_PRESSED(token = token.data))
                }
                is Results.Error -> {
                    _actionError.tryEmit(token.exception.message.toString())
                }
            }

        }
    }

}