package com.example.otaku_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StatResponse(
    @field:SerializedName("anime") val anime : List<StatisticResponse>,
    @field:SerializedName("manga") val manga : List<StatisticResponse>?
)