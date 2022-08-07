package com.example.rxjava.home.ui

import androidx.lifecycle.*
import com.example.a16_rxjava_domain.Constants
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.home.adapters.DisplayableItem
import com.example.rxjava.home.adapters.PrevAdvertising
import com.example.rxjava.home.adapters.PrevPoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ViewModel() {

    private val _actionError = MutableStateFlow("Error")
    val actionError: StateFlow<String> get() = _actionError

    private val _pageAnimePosterAction = MutableStateFlow<List<DisplayableItem>>(emptyList())
    val pageAnimePosterAction: StateFlow<List<DisplayableItem>> = _pageAnimePosterAction

    private val list = mutableListOf<DisplayableItem>(PrevAdvertising()).also {

        getList()

    }


    private fun getList() {
        viewModelScope.launch {

            getAnimePrevPosterActionFromGenre(listOf(Constants.ROMANTIC_ID), "Романтика")
            delay(100)
            getAnimePrevPosterActionFromGenre(listOf(Constants.SHOUNEN_ID), "Сёнен")
            delay(200)
            getAnimePrevPosterActionFromGenre(listOf(Constants.DRAMA_ID), "Драма")
            delay(300)
            getAnimePrevPosterActionFromGenre(listOf(Constants.DEMONS_ID), "Демоны")
            delay(400)
            getAnimePrevPosterActionFromGenre(listOf(Constants.SHOUJO_ID), "Сёдзё")
            delay(500)
            getAnimePrevPosterActionFromGenre(listOf(Constants.HAREM_ID), "Гарем")

        }
    }

    private fun getAnimePrevPosterActionFromGenre(genresId: List<Int>, genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimePrevPosterFromGenreUseCase.execute(genresId)) {
                is Results.Success -> {
                    list.add(PrevPoster(genreName, response.data))
                    _pageAnimePosterAction.value = list
                }
                is Results.Error -> {
                    list.add(PrevPoster(genreName))
                    _pageAnimePosterAction.value = list
                    _actionError.value = response.exception.message.toString()
                }
            }
        }
    }


}
