package com.example.otaku.app

import android.app.Application
import android.app.Service
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.*
import com.example.otaku.app.di.AppComponent
import com.example.otaku.app.di.DaggerAppComponent
import com.example.otaku.app.local.LocalWorker
import com.example.otaku.app.local.LocalWorkerFactory
import com.example.otaku_data.di.DataModule
import com.example.otaku_domain.IS_DAY_THEME
import com.example.otaku_domain.IS_NIGHT_THEME
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.IS_AUTO_THEME
import com.google.android.material.color.DynamicColors
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import leakcanary.LeakCanary
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class App : Application(), HasServiceInjector {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: LocalWorkerFactory

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferencesHelper

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(app = this)
        DynamicColors.applyToActivitiesIfAvailable(this)


//        initWorkManager()
        setDayNightMode()


    }

    fun setDayNightMode() {
        when (sharedPreferenceHelper.getDayNightTheme()) {
            IS_DAY_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            IS_NIGHT_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            IS_AUTO_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }


    private fun initWorkManager() {
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )
        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            LocalWorker::class.java, 2, TimeUnit.DAYS
        )
            .addTag(TAG_WORK_MANAGER).setConstraints(networkConstraints).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            TAG_WORK_MANAGER,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }


    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }

    companion object {
        const val TAG_WORK_MANAGER = "local_tag_work_manager"
    }
}