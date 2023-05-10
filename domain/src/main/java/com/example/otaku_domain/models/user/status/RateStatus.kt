package com.example.otaku_domain.models.user.status

import androidx.annotation.Keep

@Keep
enum class RateStatus(val status : String) {
    WATCHING("watching"),
    PLANNED("planned"),
    REWATCHING("rewatching"),
    COMPLETED("completed"),
    ON_HOLD("on_hold"),
    DROPPED("dropped");
}