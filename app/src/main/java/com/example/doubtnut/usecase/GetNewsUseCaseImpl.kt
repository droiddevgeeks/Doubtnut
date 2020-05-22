package com.example.doubtnut.usecase

import com.example.doubtnut.model.ArticleResponse
import com.example.doubtnut.repository.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

interface GetNewsUseCase {
    fun getNewsData(country: String, apiKey: String): Single<ArticleResponse>
}

class GetNewsUseCaseImpl @Inject constructor(private val newsRepository: NewsRepository) :
    GetNewsUseCase {

    override fun getNewsData(country: String, apiKey: String): Single<ArticleResponse> =
        newsRepository.getNewsData(country, apiKey)

}