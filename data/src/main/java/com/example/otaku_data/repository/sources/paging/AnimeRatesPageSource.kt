package com.example.otaku_data.repository.sources.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.otaku_data.mapper.toListRates
import com.example.otaku_data.network.api.UserApi
import com.example.otaku_domain.models.poster.AnimePosterEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class AnimeRatesPageSource @AssistedInject constructor(
    private val userApi: UserApi,
    @Assisted("id") private val id: Long,
    @Assisted("status") private val status: String
) : PagingSource<Int, AnimePosterEntity>() {
    override fun getRefreshKey(state: PagingState<Int, AnimePosterEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimePosterEntity> {

        val page = params.key ?: 1
        val pageSize = 30

        val response =
            userApi.getUserAnimeRates(
                page = page,
                limit = pageSize,
                id = id,
                status = status
            )

        return when (response.isSuccessful) {
            true -> {
                val posters = response.body()?.toListRates()?.mapNotNull { it.anime } ?: emptyList()

                val nextKey = if (posters.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(data = posters, prevKey = prevKey, nextKey = nextKey)
            }
            else -> {
                LoadResult.Error(Exception(response.message()))
            }
        }
    }

    class Factory @Inject constructor(
        private val userApi: UserApi,
    ) {
        fun create(
            id: Long,
            status: String
        ): AnimeRatesPageSource {
            return AnimeRatesPageSource(userApi = userApi, id = id, status = status)
        }
    }
}