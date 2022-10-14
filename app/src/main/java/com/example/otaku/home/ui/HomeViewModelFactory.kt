package com.example.otaku.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*


class HomeViewModelFactory(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase,
    private val getAnimeRandomPosterUseCase: GetAnimeRandomPosterUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            getAnimePrevPosterFromGenreUseCase = getAnimePrevPosterFromGenreUseCase,
            getAnimeRandomPosterUseCase = getAnimeRandomPosterUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getFavoritesUseCase = getFavoritesUseCase
        ) as T
    }
}