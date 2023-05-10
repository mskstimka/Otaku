package com.example.otaku_data.network.models.user

import androidx.annotation.Keep
import com.example.otaku_data.network.models.user.status.RateStatusResponse
import com.example.otaku_domain.models.user.status.RateStatus
import com.example.otaku_domain.models.user.Type
import com.google.gson.annotations.SerializedName

@Keep
data class StatusResponse(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: RateStatusResponse,
    @field:SerializedName("size") val size: Int,
    @field:SerializedName("type") val type: TypeResponse
)