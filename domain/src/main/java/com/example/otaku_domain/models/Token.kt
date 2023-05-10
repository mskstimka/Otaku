package com.example.otaku_domain.models

data class Token(
        val authToken: String,
        val refreshToken: String
)