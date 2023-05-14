package com.example.otaku_data.di

import com.example.otaku_data.network.api.AnimeApi
import com.example.otaku_data.network.api.AuthApi
import com.example.otaku_data.network.api.UserApi
import com.example.otaku_data.repository.sources.anime.AnimeDataSource
import com.example.otaku_data.repository.sources.anime.AnimeDataSourceImpl
import com.example.otaku_data.repository.sources.auth.AuthDataSource
import com.example.otaku_data.repository.sources.auth.AuthDataSourceImpl
import com.example.otaku_data.repository.sources.user.UserDataSource
import com.example.otaku_data.repository.sources.user.UserDataSourceImpl
import com.example.otaku_data.repository.sources.watch.WatchDataSource
import com.example.otaku_data.repository.sources.watch.WatchDataSourceImpl
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.SHIKIMORI_URL
import com.example.otaku_domain.SHIMORI_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [BindsDataModule::class])
class DataModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        return client
    }

    @Provides
    fun provideRetrofitProvider(client: OkHttpClient): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(SHIKIMORI_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }


    @Provides
    fun provideAnimeDataSource(
        retrofit: Retrofit,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): AnimeDataSource {
        return AnimeDataSourceImpl(
            animeApi = retrofit.create(AnimeApi::class.java),
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }

    @Provides
    fun provideWatchDataSource(): WatchDataSource {

        val client = OkHttpClient.Builder().build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(SHIMORI_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        return WatchDataSourceImpl(retrofit.create(AnimeApi::class.java))
    }

    @Provides
    fun provideAuthDataSource(
        retrofit: Retrofit,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): AuthDataSource {
        return AuthDataSourceImpl(
            authApi = retrofit.create(AuthApi::class.java),
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }

    @Provides
    fun provideUserDataSource(
        retrofit: Retrofit,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): UserDataSource {
        return UserDataSourceImpl(
            userApi = retrofit.create(UserApi::class.java),
            sharedPreferencesHelper = sharedPreferencesHelper
            )
    }

}