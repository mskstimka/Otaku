package com.example.animator.details.adapters.videos

import androidx.annotation.Keep
import com.example.animator_domain.models.details.Video
import java.util.*

@Keep
data class ContainerVideos(
    val list: List<Video>,
    val id: String = UUID.randomUUID().toString()
)