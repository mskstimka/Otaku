package com.example.otaku.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.common.Results
import com.example.domain.models.user.UserBrief
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val repository: AuthRepository
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionAuth = MutableSharedFlow<Boolean>(replay = 1)
    val actionAuth: SharedFlow<Boolean> get() = _actionAuth

    fun signIn(authCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.signIn(authCode = authCode)

            when (token) {
                is Results.Success -> {
                    sharedPreferencesHelper.setLocalToken(token = token.data)
                    _actionAuth.tryEmit(true)
                }
                is Results.Error -> {
                    _actionError.tryEmit(token.exception.message.toString())
                }
            }

        }
    }


//            lifecycleScope.launch(Dispatchers.IO) {
//
//        val result = authRepository.signIn(authCode)
//
//        when (result) {
//            is Results.Success -> {
//                withContext(Dispatchers.Main) {
//                    findNavController().navigate(
//                        AuthFragmentDirections.actionAuthFragmentToUserFragment(
//                            result.data.authToken
//                        )
//                    )
//                }
//            }
//            is Results.Error -> {
//                Log.d("ERROR TOKEN", result.exception.message.toString())
//            }
//        }
//
//    }
}