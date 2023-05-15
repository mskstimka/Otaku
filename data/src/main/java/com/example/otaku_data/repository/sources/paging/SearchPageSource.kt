package com.example.otaku_data.repository.sources.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.otaku_data.mapper.toListAnimePosterEntity
import com.example.otaku_data.network.api.AnimeApi
import com.example.otaku_domain.models.poster.AnimePosterEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class SearchPageSource @AssistedInject constructor(
    private val animeApi: AnimeApi,
    @Assisted("query") private val query: String
) : PagingSource<Int, AnimePosterEntity>() {
    override fun getRefreshKey(state: PagingState<Int, AnimePosterEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimePosterEntity> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page = params.key ?: 1
        val pageSize = 20

        val response =
            animeApi.getSearchPosters(search = query, page = page, pageSize = pageSize)

        return when (response.isSuccessful) {
            true -> {
                val posters = response.body()?.toListAnimePosterEntity() ?: emptyList()
                val nextKey = if (posters.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(data = posters, prevKey = prevKey, nextKey = nextKey)
            }
            else -> {
                LoadResult.Error(Exception(response.message()))
            }
        }
    }

    class Factory @Inject constructor(private val animeApi: AnimeApi) {
        fun create(query: String): SearchPageSource {
            return SearchPageSource(animeApi, query)
        }
    }
}