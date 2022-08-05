package com.example.rxjava.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
        private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
    ) : ViewModel() {


        private val _actionError = SingleLiveEvent<String>()
        val actionError: LiveData<String> get() = _actionError

        private val _pageAnimePrevPosterAction = MutableLiveData<List<AnimePosterEntity>>()
        val pageAnimePrevPosterAction: LiveData<List<AnimePosterEntity>> get() = _pageAnimePrevPosterAction

        fun getAnimePrevPosterActionFromGenre(genre: List<Int>) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val response = getAnimePrevPosterFromGenreUseCase.execute(genre)) {
                    is Results.Success -> {
                        _pageAnimePrevPosterAction.postValue(response.data!!)
                    }
                    is Results.Error -> _actionError.postValue(response.exception.message)
                }
            }
        }

    }
