package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.example.domain.models.details.roles.Character

@Keep
data class SeyuWorksResponse(
   val characters: List<Character>
)