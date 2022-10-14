package com.example.otaku.app.di

import androidx.work.WorkerFactory
import com.example.otaku.app.local.ChildWorkerFactory
import com.example.otaku.app.local.LocalWorker
import com.example.otaku.app.local.LocalWorkerFactory
import com.example.otaku.app.local.LocalWorkManagerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(LocalWorker::class)
    fun bindPrepopulateCategoryWork(factory: LocalWorkManagerFactory): ChildWorkerFactory

    @Binds
    fun bindWorkManagerFactory(factory: LocalWorkerFactory): WorkerFactory
}