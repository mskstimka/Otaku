package com.example.rxjava.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.home.adapters.DisplayableItem
import com.example.rxjava.home.adapters.PrevPoster
import com.example.rxjava.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ViewModel() {

    init {
        getAnimePrevPosterActionFromGenre(listOf(22))

        getAnimePrevPosterActionFromGenre(listOf(23))
    }


    private val _actionError = SingleLiveEvent<String>()
    val actionError: LiveData<String> get() = _actionError

    private val _pageAnimePrevPosterAction = MutableLiveData<List<DisplayableItem>>()
    val pageAnimePrevPosterAction: LiveData<List<DisplayableItem>> get() = _pageAnimePrevPosterAction

    private val list = mutableListOf<DisplayableItem>()

    fun getAnimePrevPosterActionFromGenre(genre: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getAnimePrevPosterFromGenreUseCase.execute(genre)) {
                is Results.Success -> {

                    list.add(PrevPoster(response.data!!))
                    _pageAnimePrevPosterAction.postValue(list)
                }
                is Results.Error -> _actionError.postValue(response.exception.message)
            }
        }
    }


}
