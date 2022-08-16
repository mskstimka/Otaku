package com.example.rxjava.app

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.a16_rxjava_domain.usecases.GetAnimePrevPosterFromGenreUseCase
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class LocalWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        val foundEntry =
            workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = foundEntry?.value
            ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
        return factoryProvider.get().create(appContext, workerParameters)
    }
}


class LocalWorkManagerFactory @Inject constructor(
    private val getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : ChildWorkerFactory {
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return LocalWorker(
            appContext,
            params,
            getAnimePrevPosterFromGenreUseCase,
        )
    }
}

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}