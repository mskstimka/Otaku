package com.example.animator_data.network.models.user

import androidx.annotation.Keep
import com.example.domain.models.user.RateStatus
import com.example.domain.models.user.Type
import com.google.gson.annotations.SerializedName

@Keep
data class StatusResponse(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name") val name: RateStatus,
        @field:SerializedName("size") val size: Int,
        @field:SerializedName("type") val type: Type
)