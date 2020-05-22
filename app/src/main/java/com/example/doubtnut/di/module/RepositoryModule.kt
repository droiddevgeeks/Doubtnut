package com.example.doubtnut.di.module

import com.example.doubtnut.api.NewsApi
import com.example.doubtnut.repository.NewsRepository
import com.example.doubtnut.repository.NewsRepositoryImpl
import com.example.doubtnut.usecase.GetNewsUseCase
import com.example.doubtnut.usecase.GetNewsUseCaseImpl
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCaseImpl(newsRepository)
    }

    @Provides
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }
}