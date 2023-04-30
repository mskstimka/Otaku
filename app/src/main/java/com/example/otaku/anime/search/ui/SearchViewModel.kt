package com.example.otaku.anime.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Results
import com.example.domain.models.poster.AnimePosterEntity
import com.example.domain.usecases.GetAnimePostersFromSearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>(replay = 1)
    val actionError: SharedFlow<String> = _actionError

    private val _actionSearch = MutableSharedFlow<List<AnimePosterEntity>>(replay = 2)
    val actionSearch: SharedFlow<List<AnimePosterEntity>> = _actionSearch

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
                if (searchResult == "") {
                    _actionSearch.emit(emptyList())
                } else {
                    search(searchResult)
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun search(searchName: String) = viewModelScope.launch {
        when (val response = getAnimePostersFromSearchUseCase.execute(searchName = searchName)) {
            is Results.Success -> {
                _actionSearch.emit(response.data)
            }
            is Results.Error -> {
                _actionError.emit(response.exception.message.toString())
            }
        }
    }
}