package com.example.otaku.user.adapters.info

sealed class ActionFriends {
    data class ADD_TO_FRIENDS(val id: Long) : ActionFriends()
    data class DELETE_FRIEND(val id: Long) : ActionFriends()
}