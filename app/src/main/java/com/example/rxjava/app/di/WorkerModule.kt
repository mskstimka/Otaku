package com.example.rxjava.app.di

import androidx.work.WorkerFactory
import com.example.rxjava.app.ChildWorkerFactory
import com.example.rxjava.app.LocalWorker
import com.example.rxjava.app.LocalWorkerFactory
import com.example.rxjava.app.LocalWorkManagerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(LocalWorker::class)
    fun bindPrepopulateCategoryWork(factory: LocalWorkManagerFactory): ChildWorkerFactory

    @Binds
    fun bindWorkManagerFactory(factory: LocalWorkerFactory): WorkerFactory
}