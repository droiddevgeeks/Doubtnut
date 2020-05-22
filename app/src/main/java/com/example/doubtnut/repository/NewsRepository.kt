package com.example.doubtnut.repository

import com.example.doubtnut.model.ArticleResponse
import io.reactivex.Single

interface NewsRepository {
    fun getNewsData(country: String, apiKey: String): Single<ArticleResponse>
}