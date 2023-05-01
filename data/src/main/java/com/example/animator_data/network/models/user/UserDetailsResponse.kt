package com.example.animator_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDetailsResponse(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("stats") val stats : UserStatsResponse
)