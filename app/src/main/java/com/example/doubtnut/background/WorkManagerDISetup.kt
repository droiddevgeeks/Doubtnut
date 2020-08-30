package com.example.doubtnut.background

import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Module
internal abstract class WorkManagerBindingModule {

    @Binds
    abstract fun bindBackgroundWorkerFactory(factory: BackgroundWorkerFactory): WorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(SyncDataWorker::class)
    abstract fun bindSyncWorker(factory: SyncWorkerFactory): ChildWorkerFactory
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)