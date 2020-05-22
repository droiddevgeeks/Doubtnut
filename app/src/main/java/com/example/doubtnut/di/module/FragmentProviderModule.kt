package com.example.doubtnut.di.module

import com.example.doubtnut.ui.fragment.ArticleFragment
import com.example.doubtnut.ui.fragment.NewsDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProviderModule {

    @ContributesAndroidInjector
    abstract fun provideIssueFragment(): ArticleFragment

    @ContributesAndroidInjector
    abstract fun provideCommentFragment(): NewsDetailFragment

}