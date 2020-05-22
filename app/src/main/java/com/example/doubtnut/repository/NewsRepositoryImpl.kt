package com.example.doubtnut.repository

import com.example.doubtnut.api.NewsApi
import com.example.doubtnut.model.ArticleResponse
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) :
    NewsRepository {

    override fun getNewsData(country: String, apiKey: String): Single<ArticleResponse> {
        return newsApi.getNewsData(country, apiKey)
    }
}