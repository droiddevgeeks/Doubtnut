package com.example.doubtnut

import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.doubtnut.di.component.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class NewsApplication : DaggerApplication() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )
        Stetho.initializeWithDefaults(this)
    }
}