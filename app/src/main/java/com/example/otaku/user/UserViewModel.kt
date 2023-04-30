package com.example.otaku.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.USER_AGENT
import com.example.domain.common.Results
import com.example.domain.models.user.UserBrief
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionUserBrief = MutableSharedFlow<UserBrief>(replay = 1)
    val actionUserBrief: SharedFlow<UserBrief> get() = _actionUserBrief

    var isSavedLocalToken = sharedPreferencesHelper.getLocalToken() != null

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {

            val localToken = sharedPreferencesHelper.getLocalToken()
            if (localToken != null) {
                val user =
                    repository.getCurrentUser(
                        userAgent = USER_AGENT,
                        authHeader = localToken.authToken
                    )

                when (user) {
                    is Results.Success -> {
                        _actionUserBrief.tryEmit(user.data)
                    }
                    is Results.Error -> {
                        _actionError.tryEmit(user.exception.message.toString())
                    }
                }
            } else {
                _actionError.tryEmit("Local Token is Null")
            }
        }
    }

}