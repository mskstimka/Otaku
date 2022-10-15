package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WorkResponse(
    @SerializedName("anime") val anime: AnimePosterEntityResponse?,
    @SerializedName("role") val role: String
)