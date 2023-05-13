package com.example.otaku_data.network.models.user

import com.google.gson.annotations.SerializedName

data class UserNoticeResponse(
    @SerializedName("notice") val notice: String?
)