package com.example.otaku_data.repository.sources.watch

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.details.Translations


interface WatchDataSource {
    suspend fun getSeries(malId: Long, name: String): Results<Int>

    suspend fun getVideo(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ): Results<List<Translations>>
}


