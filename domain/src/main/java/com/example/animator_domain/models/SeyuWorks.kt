package com.example.animator_domain.models

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.Character

@Keep
data class SeyuWorks(
    val characters: List<Character>
)