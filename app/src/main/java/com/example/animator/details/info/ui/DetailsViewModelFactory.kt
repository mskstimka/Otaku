package com.example.animator.details.info.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*

class DetailsViewModelFactory(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getVideoUseCase: GetVideoUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            getAnimeDetailsFromIdUseCase = getAnimeDetailsFromIdUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getAnimeFranchisesFromIdUseCase = getAnimeFranchisesFromIdUseCase,
            getAnimeRolesFromIdUseCase = getAnimeRolesFromIdUseCase,
            getSeriesUseCase = getSeriesUseCase,
            getVideoUseCase = getVideoUseCase
        ) as T
    }
}