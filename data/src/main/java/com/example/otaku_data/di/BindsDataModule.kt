package com.example.otaku_data.di

import com.example.otaku_data.repository.AnimeRepositoryImpl
import com.example.otaku_data.repository.AuthRepositoryImpl
import com.example.otaku_data.repository.PagingRepositoryImpl
import com.example.otaku_data.repository.UserRepositoryImpl
import com.example.otaku_data.repository.sources.translate.TranslateRepositoryImpl
import com.example.otaku_domain.repository.*
import dagger.Binds
import dagger.Module

@Module
interface BindsDataModule {

    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindAnimehRepository(animeRepository: AnimeRepositoryImpl): AnimeRepository

    @Binds
    fun bindTranslateResository(translateRepository: TranslateRepositoryImpl): TranslateRepository

    @Binds
    fun bindSearchRepository(searchRepository: PagingRepositoryImpl): PagingRepository

}