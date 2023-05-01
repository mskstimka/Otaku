package com.example.animator_data.di

import android.content.Context
import com.example.animator_data.database.dao.ShikimoriDAO
import com.example.animator_data.database.dao.ShikimoriDataBase
import com.example.animator_data.network.api.AnimeApi
import com.example.animator_data.network.api.AuthApi
import com.example.animator_data.network.api.UserApi
import com.example.animator_data.repository.*
import com.example.animator_data.repository.sources.anime.AnimeDataSource
import com.example.animator_data.repository.sources.anime.AnimeDataSourceImpl
import com.example.animator_data.repository.sources.watch.WatchDataSource
import com.example.animator_data.repository.sources.watch.WatchDataSourceImpl
import com.example.animator_data.utils.SharedPreferencesHelper
import com.example.domain.SHIKIMORI_URL
import com.example.domain.SHIMORI_URL
import com.example.domain.repository.AnimeRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.google.gson.GsonBuilder
import com.google.rpc.context.AttributeContext.Auth
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
class DataModule(private val context: Context) {


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
    fun provideAnimeDataSource(retrofit: Retrofit, shikimoriDAO: ShikimoriDAO): AnimeDataSource {
        return AnimeDataSourceImpl(
            animeApi = retrofit.create(AnimeApi::class.java),
            shikimoriDAO = shikimoriDAO
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
    fun provideAuthRepository(
        retrofit: Retrofit,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApi = retrofit.create(AuthApi::class.java),
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }

    @Provides
    fun provideUserRepository(
        retrofit: Retrofit,
        authRepository: AuthRepository,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): UserRepository {
        return UserRepositoryImpl(
            userApi = retrofit.create(UserApi::class.java),
            authRepository = authRepository,
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }

    @Provides
    fun provideSharedPreference(): SharedPreferencesHelper {
        return SharedPreferencesHelper(context)
    }

    @Provides
    fun provideShikimoriDataBase(): ShikimoriDAO {
        return ShikimoriDataBase.getDatabase(context = context).getCurrencyDao()
    }

    @Provides
    fun provideAnimeRepository(
        animeDataSource: AnimeDataSource,
        watchDataSourceImpl: WatchDataSource,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            animeDataSource = animeDataSource,
            watchDataSourceImpl = watchDataSourceImpl,
            sharedPreferencesHelper = sharedPreferencesHelper
        )
    }
}