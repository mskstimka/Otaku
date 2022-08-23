package com.example.animator.app

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.animator.R
import androidx.work.*
import com.example.animator_data.di.DataModule
import com.example.animator.app.di.AppComponent
import com.example.animator.app.di.DaggerAppComponent
import com.example.animator.app.local.LocalWorker
import com.example.animator.app.local.LocalWorkerFactory
import koleton.SkeletonLoader
import koleton.SkeletonLoaderFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application(), SkeletonLoaderFactory {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: LocalWorkerFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .dataModule(DataModule(context = this))
            .build()
        appComponent.inject(app = this)
        initWorkManager()

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

    override fun newSkeletonLoader(): SkeletonLoader {
        return SkeletonLoader.Builder(this)
            .color(R.color.skeleton_color)
            .cornerRadius(radius = 15F)
            .build()
    }

    companion object {
        const val TAG_WORK_MANAGER = "local_tag_work_manager"
    }
}