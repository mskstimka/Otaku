package com.example.animator.app.local

import android.content.Context
import androidx.work.*
import androidx.work.CoroutineWorker
import com.example.animator_domain.*
import com.example.animator_domain.models.home.PrevPoster
import com.example.animator_domain.usecases.GetAnimePrevPosterFromGenreUseCase


class LocalWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private var getAnimePrevPosterFromGenreUseCase: GetAnimePrevPosterFromGenreUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        getPrevPosters(ARRAY_PREV_POSTERS)

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