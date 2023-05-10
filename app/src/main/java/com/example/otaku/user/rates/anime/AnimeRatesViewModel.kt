package com.example.otaku.user.rates.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.usecases.user.GetUserAnimeRatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AnimeRatesViewModel @Inject constructor(private val getUserAnimeRatesUseCase: GetUserAnimeRatesUseCase) :
    ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionPosters = MutableSharedFlow<List<AnimePosterEntity>>(replay = 1)
    val actionPosters: SharedFlow<List<AnimePosterEntity>> get() = _actionPosters


     fun getUserAnimeRates(
        id: Long,
        page: Int,
        limit: Int,
        status: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val animeRates = getUserAnimeRatesUseCase.execute(
                id = id,
                page = page,
                limit = limit,
                status = status
            )
            when (animeRates) {
                is Results.Success -> {
                    _actionPosters.tryEmit(animeRates.data.mapNotNull { it.anime })
                }
                is Results.Error -> {
                    _actionError.tryEmit(animeRates.exception.message.toString())
                }
            }
        }
    }
}