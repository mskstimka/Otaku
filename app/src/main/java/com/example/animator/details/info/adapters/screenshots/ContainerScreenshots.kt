package com.example.animator.details.info.adapters.screenshots

import androidx.annotation.Keep
import com.example.animator_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import java.util.*

@Keep
data class ContainerScreenshots(
    val list: List<AnimeDetailsScreenshotsEntity>,
    val id: String = "screenshots_id"
)