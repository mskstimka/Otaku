package com.example.otaku_domain.models.user

import androidx.annotation.Keep
import com.example.otaku_domain.NO_CURRENT_USER_ID

@Keep
data class UserBrief(
    val id: Long,
    val nickname: String,
    val avatar: String?,
    val image: UserImage,
    val name: String?,
    var inFriends: Boolean?
) {
    var currentUserId = NO_CURRENT_USER_ID
}