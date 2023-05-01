package com.example.domain.models.user

import androidx.annotation.Keep

@Keep
data class UserStats(
        val animeStatuses: List<Status>?,
        val mangaStatuses: List<Status>?,
        val scores : UserStat,
        val types : UserStat,
        val ratings : UserStat,
        val hasAnime: Boolean,
        val hasManga: Boolean
)