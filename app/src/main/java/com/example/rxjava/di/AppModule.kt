package com.example.rxjava.di

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.a16_rxjava_domain.usecases.*
import com.example.rxjava.R
import com.example.rxjava.ui.activities.MainActivity
import com.example.rxjava.ui.fragments.HomeFragmentDirections
import com.example.rxjava.ui.search.SearchContract
import com.example.rxjava.ui.search.SearchFragment
import com.example.rxjava.ui.search.SearchFragmentDirections
import com.example.rxjava.ui.search.SearchPresenter
import com.example.rxjava.viewmodels.details.DetailsViewModelFactory
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

//    @Provides
//    fun provideSearchFragment(): SearchContract.View {
//        return
//    }
//
//    @Provides
//    fun provideSearchPresenter(searchUseCase: GetAnimePostersFromSearchUseCase, view: SearchContract.View): SearchContract.Presenter {
//        return SearchPresenter(searchUseCase, view)
//    }
}