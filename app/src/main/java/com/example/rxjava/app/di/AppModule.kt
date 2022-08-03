package com.example.rxjava.app.di

import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.details.ui.DetailsViewModelFactory
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

}