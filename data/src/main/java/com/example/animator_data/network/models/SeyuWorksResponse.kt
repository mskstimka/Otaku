package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.example.animator_domain.models.details.roles.Character
import com.google.gson.annotations.SerializedName

@Keep
data class SeyuWorksResponse(
   val characters: List<Character>
)