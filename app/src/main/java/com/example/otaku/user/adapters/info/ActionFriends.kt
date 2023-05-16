package com.example.otaku.user.adapters.info

import com.google.errorprone.annotations.Keep

@Keep
sealed class ActionFriends {
    data class ADD_TO_FRIENDS(val id: Long) : ActionFriends()
    data class DELETE_FRIEND(val id: Long) : ActionFriends()
}