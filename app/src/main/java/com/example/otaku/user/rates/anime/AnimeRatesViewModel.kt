package com.example.otaku.user.rates.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.usecases.user.GetUserAnimeRatesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class AnimeRatesViewModel @Inject constructor(
    private val getUserAnimeRatesUseCase: Provider<GetUserAnimeRatesUseCase>
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> get() = _actionError

    private val _actionPosters = MutableStateFlow<PagingData<AnimePosterEntity>?>(null)
    val actionPosters: StateFlow<PagingData<AnimePosterEntity>?> get() = _actionPosters

    private val searchFlow = MutableSharedFlow<Pair<Long, String>>()

    init {
        initFlow()
    }

    fun getList(id: Long, status: String) {
        viewModelScope.launch {
            searchFlow.emit(id to status)
        }
    }

    private fun initFlow() {
        searchFlow
            .flatMapLatest { (id, status) ->
                getPager(id, status).flow
            }
            .cachedIn(viewModelScope)
            .onEach { _actionPosters.value = it }
            .catch { error ->
                // Обработка ошибок
                _actionError.emit(error.message ?: "An error occurred")
            }
            .launchIn(viewModelScope)
    }

    private fun getPager(id: Long, status: String): Pager<Int, AnimePosterEntity> {
        return Pager(PagingConfig(10, enablePlaceholders = false)) {
            val getUserAnimeRatesUseCase = getUserAnimeRatesUseCase.get()
            getUserAnimeRatesUseCase.execute(id = id, status = status)
        }
    }
}
