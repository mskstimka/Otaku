package com.example.otaku_data.network.models.user.status

import com.google.gson.annotations.SerializedName

enum class RateStatusResponse(val status: String) {
    @SerializedName("watching")
    WATCHING("watching"),

    @SerializedName("planned")
    PLANNED("planned"),

    @SerializedName("rewatching")
    REWATCHING("rewatching"),

    @SerializedName("completed")
    COMPLETED("completed"),

    @SerializedName("on_hold")
    ON_HOLD("on_hold"),

    @SerializedName("dropped")
    DROPPED("dropped");
}
