package com.example.a16_rxjava_data.di

import android.content.Context
import com.example.a16_rxjava_data.database.dao.ShikimoriDAO
import com.example.a16_rxjava_data.database.dao.ShikimoriDataBase
import com.example.a16_rxjava_data.network.api.ShikimoriAPI
import com.example.a16_rxjava_data.repository.AnimeDataSource
import com.example.a16_rxjava_data.repository.AnimeDataSourceImpl
import com.example.a16_rxjava_data.repository.AnimeRepositoryImpl
import com.example.a16_rxjava_domain.Constants
import com.example.a16_rxjava_domain.repository.AnimeRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule(private val context: Context) {


    @Provides
    fun provideRetrofitProvider(): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(Constants.SHIKIMORI_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideAnimeDataSource(retrofit: Retrofit, shikimoriDAO: ShikimoriDAO): AnimeDataSource {
        return AnimeDataSourceImpl(retrofit.create(ShikimoriAPI::class.java), shikimoriDAO)
    }

    @Provides
    fun provideShikimoriDataBase(context: Context): ShikimoriDAO {
        return ShikimoriDataBase.getDatabase(context).getCurrencyDao()
    }

    @Provides
    fun provideAnimeRepository(animeDataSource: AnimeDataSource): AnimeRepository {
        return AnimeRepositoryImpl(animeDataSource)
    }
}