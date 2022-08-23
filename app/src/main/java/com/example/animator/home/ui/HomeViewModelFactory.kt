package com.example.animator.home.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*

class HomeViewModelFactory(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            getAnimePrevPosterFromGenreUseCase = getAnimePrevPosterFromGenreUseCase
        ) as T
    }
}