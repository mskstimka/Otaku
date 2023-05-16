package com.example.otaku_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserNoticeResponse(
    @SerializedName("notice") val notice: String?
)