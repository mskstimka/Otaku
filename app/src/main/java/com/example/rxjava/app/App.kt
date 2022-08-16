package com.example.rxjava.app

import android.app.Application
import androidx.work.*
import com.example.a16_rxjava_data.di.DataModule
import com.example.rxjava.R
import com.example.rxjava.app.di.AppComponent
import com.example.rxjava.app.work.LocalWorker
import com.example.rxjava.app.work.WorkerInjectorFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import koleton.SkeletonLoader
import koleton.SkeletonLoaderFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application(), SkeletonLoaderFactory {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerInjectorFactory: WorkerInjectorFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        initWorkManager()

    }

    private fun initWorkManager() {
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(workerInjectorFactory).build()
        )
        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            LocalWorker::class.java, 15, TimeUnit.MINUTES
        )
            .addTag(TAG_WORK_MANAGER).setConstraints(networkConstraints).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            TAG_WORK_MANAGER,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }

    override fun newSkeletonLoader(): SkeletonLoader {
        return SkeletonLoader.Builder(this)
            .color(R.color.skeleton_color)
            .cornerRadius(15F)
            .build()
    }

    companion object {
        const val TAG_WORK_MANAGER = "local_tag_work_manager"
    }
}