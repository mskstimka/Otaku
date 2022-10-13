package com.example.animator.app.di

import android.content.Context
import com.example.animator.details.characters.ui.CharactersViewModelFactory
import com.example.animator_domain.usecases.*
import com.example.animator.details.info.ui.DetailsViewModelFactory
import com.example.animator.details.persons.ui.PersonViewModelFactory
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
        getAnimeRolesFromIdUseCase: GetAnimeRolesFromIdUseCase,
        getSeriesUseCase: GetSeriesUseCase,
        getVideoUseCase: GetVideoUseCase,
        addFavoritesUseCase: AddFavoritesUseCase,
        deleteFavoritesUseCase: DeleteFavoritesUseCase,
        checkIsFavoriteUseCase: CheckIsFavoriteUseCase
    ): DetailsViewModelFactory {
        return DetailsViewModelFactory(
            getAnimeDetailsFromIdUseCase = getAnimeDetailsFromIdUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getAnimeFranchisesFromIdUseCase = getAnimeFranchisesFromIdUseCase,
            getAnimeRolesFromIdUseCase = getAnimeRolesFromIdUseCase,
            getSeriesUseCase = getSeriesUseCase,
            getVideoUseCase = getVideoUseCase,
            addFavoritesUseCase = addFavoritesUseCase,
            deleteFavoritesUseCase = deleteFavoritesUseCase,
            checkIsFavoriteUseCase = checkIsFavoriteUseCase
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
        getAnimeScreenshotsFromIdUseCase: GetAnimeScreenshotsFromIdUseCase,
        getFavoritesUseCase: GetFavoritesUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            getAnimePrevPosterFromGenreUseCase = getAnimePrevPosterFromGenreUseCase,
            getAnimeRandomPosterUseCase = getAnimeRandomPosterUseCase,
            getAnimeScreenshotsFromIdUseCase = getAnimeScreenshotsFromIdUseCase,
            getFavoritesUseCase = getFavoritesUseCase
        )
    }

    @Provides
    fun provideCharactersViewModelFactory(
        getCharacterDetailsUseCase: GetCharacterDetailsUseCase
    ): CharactersViewModelFactory {
        return CharactersViewModelFactory(
            getCharacterDetailsUseCase = getCharacterDetailsUseCase
        )
    }

    @Provides
    fun providePersonViewModelFactory(
        getPersonUseCase: GetPersonUseCase
    ): PersonViewModelFactory {
        return PersonViewModelFactory(getPersonUseCase = getPersonUseCase)
    }

    @Provides
    fun provideSharedPreference(): SharedPreferencesHelper {
        return SharedPreferencesHelper(context)
    }


}