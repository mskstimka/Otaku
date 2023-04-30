package com.example.domain.models

data class Token(
        val authToken: String,
        val refreshToken: String
)