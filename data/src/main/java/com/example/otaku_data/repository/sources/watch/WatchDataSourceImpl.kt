package com.example.otaku_data.repository.sources.watch

import com.example.otaku_data.mapper.toTranslations
import com.example.otaku_data.network.api.AnimeApi
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.details.Translations


class WatchDataSourceImpl(
    private val animeApi: AnimeApi
) : WatchDataSource {
    override suspend fun getSeries(malId: Long, name: String): Results<Int> {
        return try {
            val response = animeApi.getEpisodes(malId, name)
            if (response.isSuccessful) {
                Results.Success(data = response.body()!!)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }

    override suspend fun getVideo(
        malId: Long,
        episode: Int,
        name: String,
        kind: String
    ): Results<List<Translations>> {

        return try {
            val response = animeApi.getVideo(
                malId = malId,
                episode = episode,
                name = name,
                hostingId = 1,
                translationType = kind
            )
            if (response.isSuccessful) {

                val item = response.body()!!.map { it.toTranslations() }
                Results.Success(data = item)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }
}