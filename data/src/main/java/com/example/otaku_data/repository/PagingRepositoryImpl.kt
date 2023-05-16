package com.example.otaku_data.repository

import androidx.paging.PagingSource
import com.example.otaku_data.repository.sources.paging.AnimeRatesPageSource
import com.example.otaku_data.repository.sources.paging.SearchPageSource
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.repository.PagingRepository
import javax.inject.Inject

class PagingRepositoryImpl @Inject constructor(
    private val searchPageSource: SearchPageSource.Factory,
    private val animeRatesPageSource: AnimeRatesPageSource.Factory
) : PagingRepository {

    override fun getSearchPaging(query: String): PagingSource<Int, AnimePosterEntity> {
        return searchPageSource.create(query)
    }

    override fun getUserAnimeRatesPaging(
        id: Long,
        status: String
    ): PagingSource<Int, AnimePosterEntity> {
        return animeRatesPageSource.create(id = id, status = status)
    }
}