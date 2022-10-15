package com.example.animator_data.network.models

import androidx.annotation.Keep
import com.example.animator_domain.models.Image
import com.google.gson.annotations.SerializedName
import com.google.type.DateTime

@Keep
data class PersonResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("russian") val nameRu: String?,
    @SerializedName("japanese") val nameJp: String?,
    @SerializedName("image") val image: Image?,
    @SerializedName("url") val url: String?,
    @SerializedName("job_title") val jobTitle: String?,
    @SerializedName("birthday") val birthDay: DateTime?,
    @SerializedName("works") val works: List<WorkResponse>?,
    @SerializedName("roles") val roles: List<SeyuWorksResponse>?
)