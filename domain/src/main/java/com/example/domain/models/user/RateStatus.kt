package com.example.domain.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class RateStatus(val status : String) {
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