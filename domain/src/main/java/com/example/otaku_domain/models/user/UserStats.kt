package com.example.otaku_domain.models.user

import androidx.annotation.Keep

@Keep
data class UserStats(
        val animeRateStatuses: List<RateStatus>?,
        val mangaRateStatuses: List<RateStatus>?,
        val scores : UserStat,
        val types : UserStat,
        val ratings : UserStat,
        val hasAnime: Boolean,
        val hasManga: Boolean
)