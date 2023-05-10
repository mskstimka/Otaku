package com.example.otaku.app.local

import android.content.Context
import androidx.work.*
import androidx.work.CoroutineWorker
import com.example.otaku_domain.models.home.PrevPoster
import com.example.otaku_domain.usecases.anime.GetAnimePrevPosterFromGenreUseCase


class LocalWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private var getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        return Result.success()
    }

    private suspend fun getPrevPosters(list: List<PrevPoster>) {
        list.forEach { item ->
            getAnimePrevPosterFromGenreUseCase.execute(
                genreId = item.genreId
            )
        }
    }
}