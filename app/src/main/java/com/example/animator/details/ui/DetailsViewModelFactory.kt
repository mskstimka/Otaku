package com.example.animator.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animator_domain.usecases.*

class DetailsViewModelFactory(
    private val getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
    private val getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
    private val getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
    private val getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            getAnimeDetailsFromIdUseCase = getAnimeDetailsFromIdUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getAnimeFranchisesFromIdUseCase = getAnimeFranchisesFromIdUseCase,
            getAnimeRolesFromIdUseCase = getAnimeRolesFromIdUseCase
        ) as T
    }
}