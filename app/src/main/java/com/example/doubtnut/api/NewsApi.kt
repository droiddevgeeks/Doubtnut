package com.example.doubtnut.api

import com.example.doubtnut.model.ArticleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getNewsData(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Single<ArticleResponse>

}