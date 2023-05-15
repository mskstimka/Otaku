package com.example.otaku.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.otaku_domain.models.poster.AnimePosterEntity
import com.example.otaku_domain.usecases.anime.GetSearchPostersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModel @Inject constructor(
    private val getSearchPostersUseCase: Provider<GetSearchPostersUseCase>
) : ViewModel() {

    private val _actionMessage = MutableSharedFlow<String>(replay = 1)
    val actionMessage: SharedFlow<String> = _actionMessage

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    private val _actionSearch: StateFlow<PagingData<AnimePosterEntity>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    val actionSearch: StateFlow<PagingData<AnimePosterEntity>> get() = _actionSearch

    private fun newPager(query: String): Pager<Int, AnimePosterEntity> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val searchPostersUseCase = getSearchPostersUseCase.get()
            searchPostersUseCase.execute(query).also { newPagingSource = it }
        }
    }

    private val _querySharedFlow = MutableSharedFlow<String>()
    private val querySharedFlow: SharedFlow<String> = _querySharedFlow

    fun onTextChanged(value: String) = viewModelScope.launch {
        _querySharedFlow.emit(value)
    }

    init {
        initFlow()
    }

    @OptIn(FlowPreview::class)
    private fun initFlow() {
        querySharedFlow.distinctUntilChanged()
            .debounce(300)
            .flowOn(Dispatchers.IO)
            .onEach { searchResult ->
                    _query.tryEmit(searchResult)
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

}