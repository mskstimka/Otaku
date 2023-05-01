package com.example.animator_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FullStatusResponse(
        @field:SerializedName("anime") val anime: List<StatusResponse>,
        @field:SerializedName("manga") val manga: List<StatusResponse>
)