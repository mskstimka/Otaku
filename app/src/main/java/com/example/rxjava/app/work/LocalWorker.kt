package com.example.rxjava.app.work

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.*
import androidx.work.CoroutineWorker
import com.example.a16_rxjava_domain.Constants
import com.example.a16_rxjava_domain.usecases.GetAnimePostersFromSearchUseCase
import com.example.a16_rxjava_domain.usecases.GetAnimePrevPosterFromGenreUseCase
import com.example.rxjava.app.firebase.MyWorker
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Provider


class LocalWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private var getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {

        getAnimePrevPosterFromGenreUseCase.execute(Constants.ROMANTIC_ID)
        delay(100)
        getAnimePrevPosterFromGenreUseCase.execute(Constants.SHOUNEN_ID)
        delay(200)
        getAnimePrevPosterFromGenreUseCase.execute(Constants.DRAMA_ID)
        delay(300)
        getAnimePrevPosterFromGenreUseCase.execute(Constants.DEMONS_ID)
        delay(400)
        getAnimePrevPosterFromGenreUseCase.execute(Constants.SHOUJO_ID)
        delay(500)
        getAnimePrevPosterFromGenreUseCase.execute(Constants.HAREM_ID)
        Log.d("LocalWorker", "Local data completed")
        return Result.success()

    }

    class Factory @Inject constructor(
        private val getAnimePrevPosterFromGenreUseCase: Provider<GetAnimePrevPosterFromGenreUseCase> // <-- Add your providers parameters
    ) : IWorkerFactory<LocalWorker> {
        override fun create(appContext: Context, params: WorkerParameters): LocalWorker {
            return LocalWorker(appContext, params, getAnimePrevPosterFromGenreUseCase.get())
        }
    }
}