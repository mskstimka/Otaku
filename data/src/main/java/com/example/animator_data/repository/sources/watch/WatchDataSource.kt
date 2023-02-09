package com.example.animator_data.repository.sources.watch

import com.example.domain.common.Results
import com.example.domain.models.details.Translations


interface WatchDataSource {
    suspend fun getSeries(malId: Long, name: String): Results<Int>

    suspend fun getVideo(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ): Results<List<Translations>>
}


