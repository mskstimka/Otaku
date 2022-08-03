package com.example.rxjava.di

import androidx.navigation.fragment.NavHostFragment
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.ui.search.SearchContract
import com.example.rxjava.ui.search.SearchFragment
import com.example.rxjava.ui.search.SearchPresenter
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
    fun provideSearchFragment(): SearchContract.View {
        return SearchFragment.newInstance()
    }

    @Provides
    fun provideSearchPresenter(searchUseCase: GetAnimePostersFromSearchUseCase, view: SearchContract.View): SearchContract.Presenter {
        return SearchPresenter(searchUseCase, view)
    }
}