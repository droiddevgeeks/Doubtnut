package com.example.doubtnut.di.module

import com.example.doubtnut.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentProviderModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

}