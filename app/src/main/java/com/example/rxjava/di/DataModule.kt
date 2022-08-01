package com.example.rxjava.di

import com.example.a16_rxjava_data.network.RetrofitProvider
import com.example.a16_rxjava_data.repository.AnimeDataSource
import com.example.a16_rxjava_data.repository.AnimeDataSourceImpl
import com.example.a16_rxjava_data.repository.AnimeRepositoryImpl
import com.example.a16_rxjava_domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideAnimeDataSource(): AnimeDataSource {
        return AnimeDataSourceImpl(RetrofitProvider.shikimoriAPI)
    }

    @Provides
    fun provideAnimeRepository(animeDataSource: AnimeDataSource): AnimeRepository {
        return AnimeRepositoryImpl(animeDataSource)
    }
}