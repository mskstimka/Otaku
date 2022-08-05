package com.example.rxjava.app.di

import com.example.a16_rxjava_data.di.DataModule
import com.example.rxjava.details.ui.DetailsFragment
import com.example.rxjava.home.ui.HomeFragment
import com.example.rxjava.search.ui.SearchFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(homeFragment: HomeFragment)
}