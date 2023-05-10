package com.example.otaku_data.network.models.user

import com.example.otaku_data.network.models.AnimePosterEntityResponse
import com.example.otaku_data.network.models.user.status.RateStatusResponse
import com.example.otaku_domain.models.user.status.RateStatus
import com.google.gson.annotations.SerializedName

data class RateResponse(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("score") val score: Int,
    @field:SerializedName("status") val status: RateStatusResponse,
    @field:SerializedName("episodes") val episodes: Int?,
    @field:SerializedName("anime") val anime: AnimePosterEntityResponse?,
)