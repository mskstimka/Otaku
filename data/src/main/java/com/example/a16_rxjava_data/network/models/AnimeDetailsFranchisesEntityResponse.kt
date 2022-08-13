package com.example.a16_rxjava_data.network.models

import androidx.annotation.Keep

@Keep
data class AnimeDetailsFranchisesEntityResponse(
    val current_id: Int,
    val nodes: List<Node>
)