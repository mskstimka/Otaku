package com.example.rxjava.di

import com.example.rxjava.ui.activities.MainActivity
import com.example.rxjava.ui.fragments.DetailsFragment
import com.example.rxjava.ui.fragments.SearchFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
}