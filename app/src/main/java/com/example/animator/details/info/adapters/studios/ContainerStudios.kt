package com.example.animator.details.info.adapters.studios

import androidx.annotation.Keep
import com.example.animator_domain.models.details.Studio
import java.util.*

@Keep
data class ContainerStudios(
    val list: List<Studio>,
    val id: String = "studios_id"
)