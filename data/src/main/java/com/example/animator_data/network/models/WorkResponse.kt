package com.example.animator_data.network.models

import com.google.gson.annotations.SerializedName


data class WorkResponse(
    @SerializedName("anime") val anime: AnimePosterEntityResponse?,
    @SerializedName("role") val role: String
)