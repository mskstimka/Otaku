package com.example.rxjava.di

import com.example.a16_rxjava_domain.repository.AnimeRepository
import com.example.a16_rxjava_domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetAnimePostersFromSearchUseCase(repository: AnimeRepository): GetAnimePostersFromSearchUseCase {
        return GetAnimePostersFromSearchUseCase(repository)
    }

    @Provides
    fun provideGetAnimeDetailsFromIdUseCase(repository: AnimeRepository): GetAnimeDetailsFromIdUseCase {
        return GetAnimeDetailsFromIdUseCase(repository)
    }

    @Provides
    fun provideGetAnimeScreenshotsFromIdUseCase(repository: AnimeRepository): GetAnimeScreenshotsFromIdUseCase {
        return GetAnimeScreenshotsFromIdUseCase(repository)
    }

    @Provides
    fun provideGetAnimeFranchisesFromIdUseCase(repository: AnimeRepository): GetAnimeFranchisesFromIdUseCase {
        return GetAnimeFranchisesFromIdUseCase(repository)
    }

    @Provides
    fun provideGetAnimeRolesFromIdUseCase(repository: AnimeRepository): GetAnimeRolesFromIdUseCase {
        return GetAnimeRolesFromIdUseCase(repository)
    }


}