package com.example.otaku.user.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.common.Results
import com.example.domain.models.user.FavoriteList
import com.example.domain.models.user.UserBrief
import com.example.domain.usecases.user.*
import com.example.otaku.user.adapters.friends.UserFriendsContainer
import com.example.otaku.user.adapters.stats.models.UserStatsContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getCurrentUserBriefUseCase: GetCurrentUserBriefUseCase,
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val getUserBriefUseCase: GetUserBriefUseCase,
    private val getUserStatsUseCase: GetUserStatsUseCase,
    private val getUserFriendsUseCase: GetUserFriendsUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionUserBrief = MutableSharedFlow<List<UserBrief>>(replay = 1)
    val actionUserBrief: SharedFlow<List<UserBrief>> get() = _actionUserBrief

    private val _actionUserFriends = MutableSharedFlow<MutableList<UserFriendsContainer>>(replay = 1)
    val actionUserFriends: SharedFlow<MutableList<UserFriendsContainer>> get() = _actionUserFriends

    private val _actionUserFavorites = MutableSharedFlow<MutableList<FavoriteList>>(replay = 1)
    val actionUserFavorites: SharedFlow<MutableList<FavoriteList>> get() = _actionUserFavorites

    private val _actionUserStats = MutableSharedFlow<MutableList<UserStatsContainer>>(replay = 1)
    val actionUserStats: SharedFlow<MutableList<UserStatsContainer>> get() = _actionUserStats

    private val _progressBarAction = MutableSharedFlow<Int>(replay = 1)
    val progressBarAction: SharedFlow<Int> get() = _progressBarAction

    private val resultsFlow = combine(
        _actionUserBrief,
        _actionUserFavorites,
        _actionUserStats,
        _actionUserFriends
    ) { userBriefList, userFavoritesList, userStatsList, userFriendsList ->
        Quadruple(userBriefList, userFavoritesList, userStatsList, userFriendsList)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            resultsFlow.collect {
                Log.d("RESULTS FLOW", "---------------")
                if (it.first.isNotEmpty() && it.second.isNotEmpty() && it.third.isNotEmpty() && it.fourth.isNotEmpty()) {
                    _progressBarAction.tryEmit(View.GONE)
                }
            }
        }
    }


    fun getUserInfo(id: Long) {
        getUserBrief(id = id)
        getUserStats(id = id)
        getUserFavorites(id = id)
        getUserFriends(id = id)
    }

    private fun getUserFavorites(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = getUserFavoritesUseCase.execute(id = id)
            when (favorites) {
                is Results.Success -> {
                    _actionUserFavorites.tryEmit(mutableListOf(favorites.data))
                }
                is Results.Error -> {
                    _actionError.tryEmit(favorites.exception.message.toString())
                }
            }
        }

    }

    private fun getUserStats(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userStats = getUserStatsUseCase.execute(id = id)
            when (userStats) {
                is Results.Success -> {
                    _actionUserStats.tryEmit(mutableListOf(UserStatsContainer(userStats.data)))
                }
                is Results.Error -> {
                    _actionError.tryEmit(userStats.exception.message.toString())
                }
            }
        }
    }

    private fun getUserBrief(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val userBrief = getUserBriefUseCase.execute(id = id)
            when (userBrief) {
                is Results.Success -> {
                    _actionUserBrief.tryEmit(listOf(userBrief.data))
                }
                is Results.Error -> {
                    _actionError.tryEmit(userBrief.exception.message.toString())
                }
            }
        }
    }

    private fun getUserFriends(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val listUserBrief = getUserFriendsUseCase.execute(id = id)
            when (listUserBrief) {
                is Results.Success -> {
                    _actionUserFriends.tryEmit(
                        mutableListOf(UserFriendsContainer(list = listUserBrief.data))
                    )
                }
                is Results.Error -> {
                    _actionError.tryEmit(listUserBrief.exception.message.toString())
                }
            }
        }
    }

}