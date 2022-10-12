package com.example.animator.app.di

import com.example.animator.app.App
import com.example.animator.details.characters.ui.CharactersFragment
import com.example.animator.details.episodes.ui.EpisodesFragment
import com.example.animator.details.info.ui.DetailsFragment
import com.example.animator.details.persons.ui.PersonFragment
import com.example.animator.home.ui.HomeFragment
import com.example.animator.search.ui.SearchFragment
import com.example.animator.settings.ui.SettingsFragment
import com.example.animator_data.di.DataModule
import dagger.Component


@Component(modules = [AppModule::class, DomainModule::class, DataModule::class, WorkerModule::class, ServiceBuilderModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(episodesFragment: EpisodesFragment)
    fun inject(charactersFragment: CharactersFragment)
    fun inject(personFragment: PersonFragment)
    fun inject(app: App)
}