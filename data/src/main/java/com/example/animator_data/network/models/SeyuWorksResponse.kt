package com.example.animator_data.network.models

import com.example.animator_domain.models.details.roles.Character
import com.google.gson.annotations.SerializedName

data class SeyuWorksResponse(
   val characters: List<Character>
)