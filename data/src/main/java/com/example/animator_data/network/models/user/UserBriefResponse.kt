package com.example.animator_data.network.models.user

import androidx.annotation.Keep
import com.example.domain.models.Image
import com.google.gson.annotations.SerializedName

@Keep
class UserBriefResponse(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("nickname") val nickname: String,
    @field:SerializedName("avatar") val avatar: String?,
    @field:SerializedName("image") val image: UserImageResponse,
    @field:SerializedName("name") val name: String?,
)