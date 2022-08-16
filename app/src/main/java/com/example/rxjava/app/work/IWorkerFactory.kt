package com.example.rxjava.app.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface IWorkerFactory<T : ListenableWorker> {
    fun create(appContext: Context, params: WorkerParameters): T
}