package com.example.otaku.app.di

import android.app.Application
import com.example.otaku.agreement.UserAgreementFragmentDialog
import com.example.otaku.anime.details.episodes.ui.EpisodesFragment
import com.example.otaku.anime.details.info.ui.DetailsFragment
import com.example.otaku.app.App
import com.example.otaku.app.activities.main.MainActivity
import com.example.otaku.app.di.modules.AppModule
import com.example.otaku.app.di.modules.ServiceBuilderModule
import com.example.otaku.app.di.modules.ViewModelModule
import com.example.otaku.app.di.modules.WorkerModule
import com.example.otaku.auth.AuthActivity
import com.example.otaku.auth.AuthFragment
import com.example.otaku.characters.ui.CharactersFragment
import com.example.otaku.home.ui.HomeFragment
import com.example.otaku.persons.ui.PersonFragment
import com.example.otaku.search.ui.SearchFragment
import com.example.otaku.settings.ui.LanguageSettingFragmentDialog
import com.example.otaku.settings.ui.SettingsFragment
import com.example.otaku.user.rates.anime.AnimeRatesFragment
import com.example.otaku.user.ui.UserFragment
import com.example.otaku_data.di.DataModule
import dagger.BindsInstance
import dagger.Component


@Component(modules = [AppModule::class, DataModule::class, WorkerModule::class, ServiceBuilderModule::class, ViewModelModule::class])
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
    fun inject(animeRatesFragment: AnimeRatesFragment)
    fun inject(authFragment: AuthFragment)
    fun inject(app: App)

    @Component.Builder
    interface ComponentBuilder {

        fun build() : AppComponent

        @BindsInstance
        fun application(application: Application) : ComponentBuilder
    }
}