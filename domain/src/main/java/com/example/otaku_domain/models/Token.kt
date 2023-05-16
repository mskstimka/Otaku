package com.example.otaku_domain.models

import androidx.annotation.Keep

@Keep
data class Token(
        val authToken: String,
        val refreshToken: String
)