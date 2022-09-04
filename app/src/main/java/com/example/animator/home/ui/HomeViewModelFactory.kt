package com.example.animator.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*

class HomeViewModelFactory(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            getAnimePrevPosterFromGenreUseCase = getAnimePrevPosterFromGenreUseCase
        ) as T
    }
}