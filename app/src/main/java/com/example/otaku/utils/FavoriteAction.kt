package com.example.otaku.utils

import com.example.otaku_domain.models.user.Type

sealed class FavoriteAction{
    data class CREATE_FAVORITE(val linkedId: Long, val linkedType: Type): FavoriteAction()
    data class DELETE_FAVORITE(val linkedId: Long, val linkedType: Type): FavoriteAction()
}