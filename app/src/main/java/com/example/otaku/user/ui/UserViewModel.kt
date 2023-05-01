package com.example.otaku.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.USER_AGENT
import com.example.domain.common.Results
import com.example.domain.models.user.*
import com.example.domain.usecases.user.GetCurrentUserBriefUseCase
import com.example.domain.usecases.user.GetUserFavoritesUseCase
import com.example.domain.usecases.user.GetUserStatsUseCase
import com.example.otaku.R
import com.example.otaku.user.adapters.stats.models.Stats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getCurrentUserBriefUseCase: GetCurrentUserBriefUseCase,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val getUserStatsUseCase: GetUserStatsUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionUserBrief = MutableSharedFlow<UserBrief>(replay = 1)
    val actionUserBrief: SharedFlow<UserBrief> get() = _actionUserBrief

    private val _actionUserFavorites = MutableSharedFlow<FavoriteList>(replay = 1)
    val actionUserFavorites: SharedFlow<FavoriteList> get() = _actionUserFavorites

    private val _actionUserStats = MutableSharedFlow<UserDetails>(replay = 1)
    val actionUserStats: SharedFlow<UserDetails> get() = _actionUserStats

    var isSavedLocalToken = sharedPreferencesHelper.getLocalToken() != null

    init {
        getCurrentUser()
    }

    fun getUserFavorites(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = getUserFavoritesUseCase.execute(id = id)
            when (favorites) {
                is Results.Success -> {
                    _actionUserFavorites.tryEmit(favorites.data)
                }
                is Results.Error -> {
                    _actionError.tryEmit(favorites.exception.message.toString())
                }
            }
        }

    }




    private fun countAvgScore(scores: List<Statistic>): Float {
        if (scores.isEmpty()) return 0f

        val sum = scores.sumBy { it.value }
        return scores
            .sumBy { it.name.toInt() * it.value }
            .toFloat()
            .div(sum)
            .toBigDecimal(MathContext(3, RoundingMode.UP))
            .toFloat()
    }

    fun getUserStats(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userStas = getUserStatsUseCase.execute(id = id)
            when (userStas) {
                is Results.Success -> {
                    _actionUserStats.tryEmit(userStas.data)
                }
                is Results.Error -> {
                    _actionError.tryEmit(userStas.exception.message.toString())
                }
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {

            val localToken = sharedPreferencesHelper.getLocalToken()
            if (localToken != null) {
                val user =
                    getCurrentUserBriefUseCase.execute(
                        userAgent = USER_AGENT,
                        accessToken = localToken.authToken
                    )

                when (user) {
                    is Results.Success -> {
                        getUserFavorites(user.data.id)
                        getUserStats(user.data.id)
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