package com.example.doubtnut.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.doubtnut.R
import com.example.doubtnut.background.SyncDataWorker
import com.example.doubtnut.background.SyncDataWorker.Companion.SYNC_DATA_WORK_NAME
import com.example.doubtnut.background.SyncDataWorker.Companion.TAG_MY_WORKED
import com.example.doubtnut.base.ApplicationConstants
import com.example.doubtnut.base.BaseActivity
import com.example.doubtnut.callback.IFragmentChangeCallback
import com.example.doubtnut.ui.fragment.ArticleFragment
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), IFragmentChangeCallback {

    private val workManager: WorkManager by lazy { WorkManager.getInstance(applicationContext) }
    private val periodicSyncDataWork: PeriodicWorkRequest by lazy {
        PeriodicWorkRequest.Builder(SyncDataWorker::class.java, 15, TimeUnit.MINUTES)
            .addTag(TAG_MY_WORKED)
            .setInputData(getInputData())
            .setConstraints(getWorkConstraint())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openArticleFragment()
        getNewsFromWorkManagerPeriodically()
        getWorkManagerWorkStatus()
    }

    private fun openArticleFragment() {
        replaceFragment(R.id.container, ArticleFragment.newInstance(), ArticleFragment.TAG)
    }

    override fun onFragmentChange(fragment: Fragment, tag: String) {
        replaceFragment(R.id.container, fragment, tag, true)
    }

    private fun getNewsFromWorkManagerPeriodically() {
        workManager.enqueueUniquePeriodicWork(
            SYNC_DATA_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncDataWork
        )
    }

    private fun getWorkConstraint() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun getInputData() = Data.Builder()
        .putString(SyncDataWorker.COUNTRY, ApplicationConstants.COUNTRY)
        .build()

    private fun getWorkManagerWorkStatus() {
        workManager.getWorkInfosByTagLiveData(TAG_MY_WORKED)
            .observe(this, Observer<List<WorkInfo>> { listOfWorkInfo ->
                if (!listOfWorkInfo.isNullOrEmpty()) {
                    val workInfo = listOfWorkInfo.first()
                    Log.i(TAG_MY_WORKED, "WorkerState:: " + workInfo.state)
                    when (workInfo.state) {
                        WorkInfo.State.ENQUEUED -> showToast("ENQUEUED i.e Finished")
                        WorkInfo.State.RUNNING -> showToast("RUNNING")
                        WorkInfo.State.SUCCEEDED -> showToast("SUCCEEDED")
                        WorkInfo.State.CANCELLED -> showToast("CANCELLED")
                        WorkInfo.State.BLOCKED -> showToast("BLOCKED")
                        else -> showToast("Nothing happening")
                    }
                }
            })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
