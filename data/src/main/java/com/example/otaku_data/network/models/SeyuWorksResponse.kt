package com.example.otaku_data.network.models

import androidx.annotation.Keep
import com.example.otaku_domain.models.details.roles.Character

@Keep
data class SeyuWorksResponse(
   val characters: List<Character>
)