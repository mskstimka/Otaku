package com.example.animator_data.network.models.user

import com.example.animator_data.network.models.AnimePosterEntityResponse
import com.example.domain.models.user.RateStatus
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class RateResponse(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("score") val score: Int,
        @field:SerializedName("status") val status: RateStatus,
        @field:SerializedName("episodes") val episodes: Int?,
        @field:SerializedName("anime") val anime: AnimePosterEntityResponse?,
)