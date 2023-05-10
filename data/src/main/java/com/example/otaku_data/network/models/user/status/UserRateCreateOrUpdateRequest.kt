package com.example.otaku_data.network.models.user.status

import com.google.gson.annotations.SerializedName

data class UserRateCreateOrUpdateRequest(
        @field:SerializedName("user_rate") val userRate: UserRateResponse
)