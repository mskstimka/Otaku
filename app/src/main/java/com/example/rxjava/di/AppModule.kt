package com.example.rxjava.di

import android.app.Application
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.viewmodels.details.DetailsViewModelFactory
import com.example.rxjava.viewmodels.search.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {


    @Provides
    fun provideDetailsViewModelFactory(
        getAnimeDetailsFromIdUseCase: GetAnimeDetailsFromIdUseCase,
        getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
        getAnimeFranchisesFromIdUseCase: GetAnimeFranchisesFromIdUseCase,
        getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase
    ): DetailsViewModelFactory {
        return DetailsViewModelFactory(
            getAnimeDetailsFromIdUseCase,
            getAnimeScreenshotsFromIdUseCase,
            getAnimeFranchisesFromIdUseCase,
            getAnimeRolesFromIdUseCase
        )
    }

    @Provides
    fun provideSearchViewModelFactory(
        getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase
    ): SearchViewModelFactory {
        return SearchViewModelFactory(getAnimePostersFromSearchUseCase)
    }
}