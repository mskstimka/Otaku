package com.example.animator.home.ui

import androidx.lifecycle.*
import com.example.animator_domain.*
import com.example.animator_domain.common.Results
import com.example.animator_domain.usecases.*
import com.example.animator.home.adapters.models.DisplayableItem
import com.example.animator.home.adapters.models.HomePosterEntity
import com.example.animator.home.adapters.models.HomeGenreEntity
import com.example.animator_domain.models.home.PrevPoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ViewModel() {

    private val _actionError = MutableSharedFlow<String>()
    val actionError: SharedFlow<String> = _actionError

    private val _pageAnimePosterAction = MutableStateFlow<List<DisplayableItem>>(emptyList())
    val pageAnimePosterAction: StateFlow<List<DisplayableItem>> = _pageAnimePosterAction

    private val list = mutableListOf<DisplayableItem>(HomePosterEntity()).also {
        getList(
            ARRAY_PREV_POSTERS
        )
    }

    private fun getList(list: List<PrevPoster>) {
        viewModelScope.launch {
            list.forEach { item ->
                getAnimePrevPosterActionFromGenre(
                    genresId = item.genreId,
                    genreName = item.genreName
                )
            }
        }
    }

    private fun getAnimePrevPosterActionFromGenre(genresId: Int, genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimePrevPosterFromGenreUseCase.execute(genresId)) {
                is Results.Success -> {
                    list.add(HomeGenreEntity(genreName, response.data))
                    _pageAnimePosterAction.value = list
                }
                is Results.Error -> {
                    list.add(HomeGenreEntity(genreName))
                    _pageAnimePosterAction.value = list
                    _actionError.emit(response.exception.message.toString())
                }
            }
        }
    }
}
