package com.example.otaku.user.adapters.stats.models

import com.example.domain.models.user.RateStatus
import com.example.domain.models.user.Status
import com.example.domain.models.user.UserDetails
import com.example.otaku.R

data class UserStatsContainer(val details: UserDetails) {
    val others =
        details.stats.animeStatuses?.countRate { it.name == RateStatus.PLANNED || it.name == RateStatus.WATCHING || it.name == RateStatus.ON_HOLD || it.name == RateStatus.DROPPED }
            ?: 0

    val statsList: List<Stats> by lazy {
        with(details.stats) {
            listOf(
                Stats(animeStatuses?.countRate { it.name == RateStatus.PLANNED } ?: 0,
                    RateStatus.PLANNED,
                    R.color.others_status_color),
                Stats(animeStatuses?.countRate { it.name == RateStatus.COMPLETED } ?: 0,
                    RateStatus.COMPLETED,
                    R.color.completed_status_color),
                Stats(animeStatuses?.countRate { it.name == RateStatus.WATCHING } ?: 0,
                    RateStatus.WATCHING,
                    R.color.others_status_color),
                Stats(animeStatuses?.countRate { it.name == RateStatus.ON_HOLD } ?: 0,
                    RateStatus.ON_HOLD,
                    R.color.others_status_color),
                Stats(animeStatuses?.countRate { it.name == RateStatus.DROPPED } ?: 0,
                    RateStatus.DROPPED,
                    R.color.others_status_color)
            )
        }
    }

    private inline fun List<Status>.countRate(crossinline block: (Status) -> Boolean): Int =
        sumOf { if (block.invoke(it)) it.size else 0 }

}