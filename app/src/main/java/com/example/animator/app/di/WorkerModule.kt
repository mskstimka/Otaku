package com.example.animator.app.di

import androidx.work.WorkerFactory
import com.example.animator.app.local.ChildWorkerFactory
import com.example.animator.app.local.LocalWorker
import com.example.animator.app.local.LocalWorkerFactory
import com.example.animator.app.local.LocalWorkManagerFactory
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