package com.example.animator.app.di

import com.example.animator_data.di.DataModule
import com.example.animator.app.App
import com.example.animator.details.ui.DetailsFragment
import com.example.animator.home.ui.HomeFragment
import com.example.animator.search.ui.SearchFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class, WorkerModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(app: App)
}