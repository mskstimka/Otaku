package com.example.animator_data.repository

import android.util.Log
import com.example.animator_data.mapper.AnimeWatcherResponseMapper
import com.example.animator_data.network.api.AnimeApi
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.details.Translation


class WatchDataSourceImpl(
    private val animeApi: AnimeApi
) : WatchDataSource {
    override suspend fun getSeries(malId: Long, name: String): Results<Int> {
        return try {
            val response = animeApi.getEpisodes(malId, name)
            if (response.isSuccessful) {
                Log.d("EPISODES", response.body().toString())
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
    ): Results<List<Translation>> {

        return try {
            val response = animeApi.getVideo(
                malId = malId,
                episode = episode,
                name = name,
                hostingId = 1,
                translationType = kind
            )
            if (response.isSuccessful) {

                val item = response.body()!!.map { AnimeWatcherResponseMapper.convertResponse(it) }
                Results.Success(data = item)
            } else {
                Results.Error(exception = Exception(response.message()))
            }
        } catch (e: Exception) {
            Results.Error(exception = e)
        }
    }
}