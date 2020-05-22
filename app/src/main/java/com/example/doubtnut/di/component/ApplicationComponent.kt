package com.example.doubtnut.di.component

import com.example.doubtnut.NewsApplication
import com.example.doubtnut.di.module.ApiModule
import com.example.doubtnut.di.module.ActivityBindingModule
import com.example.doubtnut.di.module.ViewModelModule
import com.example.doubtnut.di.scope.AppScope
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        ApiModule::class]
)
interface ApplicationComponent : AndroidInjector<NewsApplication> {

    @Component.Factory
    abstract class Builder: AndroidInjector.Factory<NewsApplication>
}