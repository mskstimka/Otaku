package com.example.otaku_data.di

import com.example.otaku_data.repository.AnimeRepositoryImpl
import com.example.otaku_data.repository.AuthRepositoryImpl
import com.example.otaku_data.repository.UserRepositoryImpl
import com.example.otaku_domain.repository.AnimeRepository
import com.example.otaku_domain.repository.AuthRepository
import com.example.otaku_domain.repository.UserRepository
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

}