package com.example.rxjava.app.work

import androidx.work.ListenableWorker
import com.google.android.datatransport.runtime.dagger.multibindings.IntoMap
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
interface WorkersModule {

    @Binds
    @IntoMap
    @WorkerKey(LocalWorker::class)
    fun bindLocalWorker(worker: LocalWorker.Factory): IWorkerFactory<out ListenableWorker>

}