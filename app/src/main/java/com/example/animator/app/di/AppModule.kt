package com.example.animator.app.di

import android.content.Context
import com.example.animator_domain.usecases.*
import com.example.animator.details.ui.DetailsViewModelFactory
import com.example.animator.home.ui.HomeViewModelFactory
import com.example.animator.search.ui.SearchViewModelFactory
import com.example.animator.utils.SharedPreferencesHelper
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideDetailsViewModelFactory(
        getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
        getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
        getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
        getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase
    ): DetailsViewModelFactory {
        return DetailsViewModelFactory(
            getAnimeDetailsFromIdUseCase = getAnimeDetailsFromIdUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getAnimeFranchisesFromIdUseCase = getAnimeFranchisesFromIdUseCase,
            getAnimeRolesFromIdUseCase = getAnimeRolesFromIdUseCase
        )
    }

    @Provides
    fun provideSearchViewModelFactory(
        getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase
    ): SearchViewModelFactory {
        return SearchViewModelFactory(
            getAnimePostersFromSearchUseCase = getAnimePostersFromSearchUseCase
        )
    }

    @Provides
    fun provideHomeViewModelFactory(
        getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase,
        getAnimeRandomPosterUseCase: GetAnimeRandomPosterUseCase,
        getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            getAnimePrevPosterFromGenreUseCase = getAnimePrevPosterFromGenreUseCase,
            getAnimeRandomPosterUseCase = getAnimeRandomPosterUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase
        )
    }

    @Provides
    fun provideSharedPreference(): SharedPreferencesHelper {
        return SharedPreferencesHelper(context)
    }

}