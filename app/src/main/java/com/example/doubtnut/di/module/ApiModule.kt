package com.example.doubtnut.di.module

import com.example.doubtnut.api.ApiConstant
import com.example.doubtnut.api.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [InterceptorModule::class, RepositoryModule::class])
class ApiModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstant.NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideApiInterface(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

}