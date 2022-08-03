package com.example.rxjava.di

import com.example.a16_rxjava_data.di.DataModule
import com.example.rxjava.ui.fragments.DetailsFragment
import com.example.rxjava.ui.search.SearchFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
}