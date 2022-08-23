package com.example.animator_data.network.models

import androidx.annotation.Keep

@Keep
data class AnimeDetailsFranchisesEntityResponse(
    val current_id: Int,
    val nodes: List<Node>
)