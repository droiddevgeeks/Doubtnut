package com.example.doubtnut.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.doubtnut.base.ApplicationConstants
import com.example.doubtnut.storage.ArticleDAO
import com.example.doubtnut.usecase.GetNewsUseCase
import io.reactivex.disposables.CompositeDisposable


class SyncDataWorker(
    context: Context,
    workerParam: WorkerParameters,
    private val useCase: GetNewsUseCase,
    private val articleDAO: ArticleDAO
) :
    Worker(context, workerParam) {

    companion object {
        const val TAG_MY_WORKED = "SyncDataWorker"
        const val WORKER_RESULT = "worker_result"
        const val SYNC_DATA_WORK_NAME = "sync_data_work_name"
        const val COUNTRY = "country"
    }

    private val disposable by lazy { CompositeDisposable() }

    /**
     * This method run on background thread
     */
    override fun doWork(): Result {
        Log.d(TAG_MY_WORKED, "Fetching Data from Remote host")
        val country = inputData.getString(COUNTRY) ?: ApplicationConstants.COUNTRY
        fetchNewsData(country)
        return Result.success()
    }

    private fun fetchNewsData(country: String) {
        val issueDisposable = useCase.getNewsData(country)
            .subscribe(
                { response ->
                    Log.d("$TAG_MY_WORKED:API CALL", "Total Article::${response.totalResults}")
                    response.articles?.let { articleDAO.insertAll(it) }
                },
                { Result.failure() }
            )
        disposable.add(issueDisposable)
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG_MY_WORKED, "OnStopped called for this worker")
    }
}