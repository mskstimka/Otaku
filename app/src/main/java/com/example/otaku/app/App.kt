package com.example.otaku.app

import android.app.Application
import android.app.Service
import androidx.work.*
import com.example.otaku.app.di.AppComponent
import com.example.otaku.app.di.modules.AppModule
import com.example.otaku.app.di.DaggerAppComponent
import com.example.otaku.app.local.LocalWorker
import com.example.otaku.app.local.LocalWorkerFactory
import com.example.animator_data.di.DataModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class App : Application(), HasServiceInjector{

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: LocalWorkerFactory

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .dataModule(DataModule(context = applicationContext))
            .appModule(AppModule(context = applicationContext))
            .build()
        appComponent.inject(app = this)
//        initWorkManager()

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

    companion object {
        const val TAG_WORK_MANAGER = "local_tag_work_manager"
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }
}