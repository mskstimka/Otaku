package com.example.animator_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class StatisticResponse(
        @field:SerializedName("name") val name : String,
        @field:SerializedName("value") val value : Int
)