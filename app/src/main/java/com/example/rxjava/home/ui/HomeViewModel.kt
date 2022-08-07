package com.example.rxjava.home.ui

import androidx.lifecycle.*
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.home.adapters.DisplayableItem
import com.example.rxjava.home.adapters.PrevAdvertising
import com.example.rxjava.home.adapters.PrevPoster
import com.example.rxjava.home.adapters.PrevTitle
import com.example.rxjava.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.concurrent.Flow

class HomeViewModel(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ViewModel() {


    private val _actionError = SingleLiveEvent<String>()
    val actionError: LiveData<String> get() = _actionError


    private val _pageAnimePosterAction = MutableStateFlow<List<DisplayableItem>>(emptyList())
    val pageAnimePosterAction: StateFlow<List<DisplayableItem>> = _pageAnimePosterAction



    private val list = mutableListOf<DisplayableItem>(PrevAdvertising()).also {

        getList()

    }


    private fun getList() {
        viewModelScope.launch {

            getAnimePrevPosterActionFromGenre(listOf(22), "Романтика")
            delay(100)
            getAnimePrevPosterActionFromGenre(listOf(27), "Сёнен")
            delay(200)
            getAnimePrevPosterActionFromGenre(listOf(8), "Драма")
            delay(300)
            getAnimePrevPosterActionFromGenre(listOf(6), "Демоны")
            delay(400)
            getAnimePrevPosterActionFromGenre(listOf(25), "Сёдзё")
            delay(500)
            getAnimePrevPosterActionFromGenre(listOf(35), "Гарем")

        }
    }

    private fun getAnimePrevPosterActionFromGenre(genresId: List<Int>, genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val response = getAnimePrevPosterFromGenreUseCase.execute(genresId)) {
                is Results.Success -> {
                    list.add(PrevPoster(genreName, response.data!!))
                    _pageAnimePosterAction.value = list
                }
                is Results.Error -> {
                    list.add(PrevPoster(genreName))
                    _pageAnimePosterAction.value = list
                    _actionError.postValue(response.exception.message)
                }
            }
        }
    }


}
