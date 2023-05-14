package com.example.otaku.app.local

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.otaku_data.utils.SharedPreferencesHelper
import com.example.otaku_domain.common.Results
import com.example.otaku_domain.models.Token
import com.example.otaku_domain.models.home.PrevPoster
import com.example.otaku_domain.usecases.anime.GetAnimePrevPosterFromGenreUseCase
import com.example.otaku_domain.usecases.auth.GetAccessTokenUseCase
import com.example.otaku_domain.usecases.auth.RefreshTokenUseCase
import javax.inject.Inject


class LocalWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val token = sharedPreferencesHelper.getLocalToken()

        return if (token != null) {
            when (refreshTokenUseCase.execute(token = token)) {
                is Results.Success -> Result.success()
                is Results.Error -> Result.failure()
            }
        } else {
            Result.failure()
        }
    }

}