package com.example.otaku.app.di.modules

import com.example.animator_domain.repository.AnimeRepository
import com.example.animator_domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetAnimePostersFromSearchUseCase(repository: AnimeRepository): GetAnimePostersFromSearchUseCase {
        return GetAnimePostersFromSearchUseCase(repository = repository)
    }

    @Provides
    fun provideGetAnimeDetailsFromIdUseCase(repository: AnimeRepository): GetAnimeDetailsFromIdUseCase {
        return GetAnimeDetailsFromIdUseCase(repository = repository)
    }

    @Provides
    fun provideGetAnimeScreenshotsFromIdUseCase(repository: AnimeRepository): GetAnimeScreenshotsFromIdUseCase {
        return GetAnimeScreenshotsFromIdUseCase(repository = repository)
    }

    @Provides
    fun provideGetAnimeFranchisesFromIdUseCase(repository: AnimeRepository): GetAnimeFranchisesFromIdUseCase {
        return GetAnimeFranchisesFromIdUseCase(repository = repository)
    }

    @Provides
    fun provideGetAnimeRolesFromIdUseCase(repository: AnimeRepository): GetAnimeRolesFromIdUseCase {
        return GetAnimeRolesFromIdUseCase(repository = repository)
    }

    @Provides
    fun provideGetAnimePrevPostersFromGenreUseCase(repository: AnimeRepository): GetAnimePrevPosterFromGenreUseCase {
        return GetAnimePrevPosterFromGenreUseCase(repository = repository)
    }

    @Provides
    fun provideGetAnimeRandomPosterUseCase(repository: AnimeRepository): GetAnimeRandomPosterUseCase {
        return GetAnimeRandomPosterUseCase(repository = repository)
    }


    @Provides
    fun provideGetSeriesUseCase(repository: AnimeRepository): GetSeriesUseCase {
        return GetSeriesUseCase(repository = repository)
    }

    @Provides
    fun provideGetVideoUseCase(repository: AnimeRepository): GetVideoUseCase {
        return GetVideoUseCase(repository = repository)
    }

    @Provides
    fun provideGetCharactersDetailsUseCase(repository: AnimeRepository): GetCharacterDetailsUseCase {
        return GetCharacterDetailsUseCase(repository = repository)
    }

    @Provides
    fun provideGetPersonUseCase(repository: AnimeRepository): GetPersonUseCase {
        return GetPersonUseCase(repository = repository)
    }

    @Provides
    fun provideGetFavoritesUseCase(repository: AnimeRepository): GetFavoritesUseCase {
        return GetFavoritesUseCase(repository = repository)
    }

    @Provides
    fun provideAddFavoritesUseCase(repository: AnimeRepository): AddFavoritesUseCase {
        return AddFavoritesUseCase(repository = repository)
    }

    @Provides
    fun provideDeleteFavoritesUseCase(repository: AnimeRepository): DeleteFavoritesUseCase {
        return DeleteFavoritesUseCase(repository = repository)
    }

    @Provides
    fun provideCheckIsFavoriteUseCase(repository: AnimeRepository): CheckIsFavoriteUseCase {
        return CheckIsFavoriteUseCase(repository = repository)
    }

}