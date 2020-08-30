package com.example.doubtnut.background

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.doubtnut.storage.ArticleDAO
import com.example.doubtnut.usecase.GetNewsUseCase
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor(
    private val useCase: GetNewsUseCase,
    private val articleDAO: ArticleDAO
) :
    ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return SyncDataWorker(appContext, params, useCase, articleDAO)
    }
}