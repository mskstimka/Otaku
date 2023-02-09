package com.example.otaku.app.di.modules

import android.content.Context
import com.example.otaku.anime.details.characters.ui.CharactersViewModelFactory
import com.example.domain.usecases.*
import com.example.otaku.anime.details.info.ui.DetailsViewModelFactory
import com.example.otaku.anime.details.persons.ui.PersonViewModelFactory
import com.example.otaku.anime.home.ui.HomeViewModelFactory
import com.example.otaku.anime.search.ui.SearchViewModelFactory
import com.example.animator_data.utils.SharedPreferencesHelper
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
        checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
        sharedPreferencesHelper: SharedPreferencesHelper
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
            checkIsFavoriteUseCase = checkIsFavoriteUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper
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
        getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): CharactersViewModelFactory {
        return CharactersViewModelFactory(
            getCharacterDetailsUseCase = getCharacterDetailsUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }

    @Provides
    fun providePersonViewModelFactory(
        getPersonUseCase: GetPersonUseCase,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): PersonViewModelFactory {
        return PersonViewModelFactory(
            getPersonUseCase = getPersonUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }

}