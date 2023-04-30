package com.example.otaku.app.di

import com.example.otaku.app.App
import com.example.otaku.anime.details.characters.ui.CharactersFragment
import com.example.otaku.anime.details.episodes.ui.EpisodesFragment
import com.example.otaku.anime.details.info.ui.DetailsFragment
import com.example.otaku.anime.details.persons.ui.PersonFragment
import com.example.otaku.anime.home.ui.HomeFragment
import com.example.otaku.anime.search.ui.SearchFragment
import com.example.otaku.settings.ui.SettingsFragment
import com.example.animator_data.di.DataModule
import com.example.otaku.agreement.UserAgreementFragmentDialog
import com.example.otaku.app.activities.MainActivity
import com.example.otaku.app.di.modules.*
import com.example.otaku.auth.AuthActivity
import com.example.otaku.settings.ui.LanguageSettingFragmentDialog
import com.example.otaku.user.UserFragment
import dagger.Component


@Component(modules = [AppModule::class, DomainModule::class, DataModule::class, WorkerModule::class, ServiceBuilderModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(detailsFragment: DetailsFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(episodesFragment: EpisodesFragment)
    fun inject(charactersFragment: CharactersFragment)
    fun inject(personFragment: PersonFragment)
    fun inject(languageSettingFragmentDialog: LanguageSettingFragmentDialog)
    fun inject(userAgreementFragmentDialog: UserAgreementFragmentDialog)
    fun inject(mainActivity: MainActivity)
    fun inject(userFragment: UserFragment)
    fun inject(authActivity: AuthActivity)
    fun inject(app: App)
}