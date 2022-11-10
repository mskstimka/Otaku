package com.example.otaku.details.info.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*
import com.example.otaku.utils.SharedPreferencesHelper

class DetailsViewModelFactory(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getVideoUseCase: GetVideoUseCase,
    private val addFavoritesUseCase: AddFavoritesUseCase,
    private val deleteFavoritesUseCase: DeleteFavoritesUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            getAnimeDetailsFromIdUseCase = getAnimeDetailsFromIdUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getAnimeFranchisesFromIdUseCase = getAnimeFranchisesFromIdUseCase,
            getAnimeRolesFromIdUseCase = getAnimeRolesFromIdUseCase,
            getSeriesUseCase = getSeriesUseCase,
            getVideoUseCase = getVideoUseCase,
            addFavoritesUseCase = addFavoritesUseCase,
            deleteFavoritesUseCase = deleteFavoritesUseCase,
            checkIsFavoriteUseCase = checkIsFavoriteUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper
        ) as T
    }
}