package com.example.rxjava.app.di

import android.app.Application
import android.content.Context
import com.example.a16_rxjava_data.di.DataModule
import com.example.rxjava.app.App
import com.example.rxjava.app.work.WorkersModule
import com.example.rxjava.details.ui.DetailsFragment
import com.example.rxjava.home.ui.HomeFragment
import com.example.rxjava.search.ui.SearchFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Suppress("unused")
@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class, WorkersModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(homeFragment: HomeFragment)
}