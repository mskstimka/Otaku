package com.example.animator_data.network.models.user

import com.google.gson.annotations.SerializedName


data class UserHistoryResponse(
        @field:SerializedName("id") val id : Long,
        @field:SerializedName("created_at") val dateCreated : String,
        @field:SerializedName("description") val description : String,
        @field:SerializedName("target") val target : LinkedContentResponse?
)