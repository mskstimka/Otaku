package com.example.otaku_data.network.models.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserImageResponse(
    @SerializedName("x160") var x160: String? = null,
    @SerializedName("x148") var x148: String? = null,
    @SerializedName("x80") var x80: String? = null,
    @SerializedName("x64") var x64: String? = null,
    @SerializedName("x48") var x48: String? = null,
    @SerializedName("x32") var x32: String? = null,
    @SerializedName("x16") var x16: String? = null

)
