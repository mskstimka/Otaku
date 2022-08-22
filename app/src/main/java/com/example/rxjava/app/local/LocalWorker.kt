package com.example.rxjava.app.local

import android.content.Context
import android.util.Log
import androidx.work.*
import androidx.work.CoroutineWorker
import com.example.a16_rxjava_domain.*
import com.example.a16_rxjava_domain.usecases.GetAnimePrevPosterFromGenreUseCase
import kotlinx.coroutines.*


class LocalWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private var getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        getAnimePrevPosterFromGenreUseCase.execute(genreId = ROMANTIC_ID)
        delay(100)
        getAnimePrevPosterFromGenreUseCase.execute(genreId = SHOUNEN_ID)
        delay(200)
        getAnimePrevPosterFromGenreUseCase.execute(genreId = DRAMA_ID)
        delay(300)
        getAnimePrevPosterFromGenreUseCase.execute(genreId = DEMONS_ID)
        delay(400)
        getAnimePrevPosterFromGenreUseCase.execute(genreId = SHOUJO_ID)
        delay(500)
        getAnimePrevPosterFromGenreUseCase.execute(genreId = HAREM_ID)
        return Result.success()
    }
}