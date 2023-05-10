package com.example.otaku.anime.details.info.adapters.screenshots

import androidx.annotation.Keep
import com.example.otaku_domain.models.details.screenshots.AnimeDetailsScreenshotsEntity

@Keep
data class ContainerScreenshots(
    val list: List<AnimeDetailsScreenshotsEntity>,
    val id: String = "screenshots_id"
)