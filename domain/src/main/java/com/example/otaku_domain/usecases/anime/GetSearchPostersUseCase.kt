package com.example.otaku_domain.usecases.anime

import androidx.paging.PagingSource
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.repository.PagingRepository
import javax.inject.Inject

class GetSearchPostersUseCase @Inject constructor(private val pagingRepository: PagingRepository) {

    fun execute(query: String): PagingSource<Int, AnimePosterEntity> {
        return pagingRepository.getSearchPaging(query)
    }
}