package com.example.otaku_data.network.models.user.status

import com.google.gson.annotations.SerializedName

data class UserRateResponse(
        @field:SerializedName("id") val id: Long? = null,
        @field:SerializedName("user_id") val userId: Long? = null,
        @field:SerializedName("target_id") val targetId: Long? = null,
        @field:SerializedName("target_type") val targetType: String? = null,
        @field:SerializedName("score") val score: Double? = null,
        @field:SerializedName("status") val status: String? = null,
        @field:SerializedName("rewatches") val rewatches: Int? = null,
        @field:SerializedName("episodes") val episodes: Int? = null,
        @field:SerializedName("volumes") val volumes: Int? = null,
        @field:SerializedName("chapters") val chapters: Int? = null,
        @field:SerializedName("text") val text: String? = null,
)