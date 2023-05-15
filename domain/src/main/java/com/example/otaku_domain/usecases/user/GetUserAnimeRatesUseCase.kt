package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.models.user.status.RateStatus
import com.example.otaku_domain.repository.PagingRepository
import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class GetUserAnimeRatesUseCase @Inject constructor(private val pagingRepository: PagingRepository) {

    fun execute(
        id: Long,
        status: String
    ) = pagingRepository.getUserAnimeRatesPaging(id = id, status = status)
}