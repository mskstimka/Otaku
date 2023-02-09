package com.example.domain.models

import androidx.annotation.Keep
import com.example.domain.models.details.roles.Character

@Keep
data class SeyuWorks(
    val characters: List<Character>
)