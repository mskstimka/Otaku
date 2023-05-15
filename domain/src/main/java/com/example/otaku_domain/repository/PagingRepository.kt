package com.example.otaku_domain.repository

import androidx.paging.PagingSource
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.models.user.Rate
import com.example.otaku_domain.models.user.status.RateStatus

interface PagingRepository {

    fun getSearchPaging(query: String): PagingSource<Int, AnimePosterEntity>

    fun getUserAnimeRatesPaging(id: Long, status: String): PagingSource<Int, AnimePosterEntity>

}