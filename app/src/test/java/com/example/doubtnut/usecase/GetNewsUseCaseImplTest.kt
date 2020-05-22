package com.example.doubtnut.usecase

import com.example.doubtnut.model.ArticleResponse
import com.example.doubtnut.repository.NewsRepositoryImpl
import com.example.doubtnut.testutil.Rx2SchedulersOverrideRule
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetNewsUseCaseImplTest {


    @JvmField
    @Rule
    val rx2SchedulersOverrideRule = Rx2SchedulersOverrideRule()

    private lateinit var usecase: GetNewsUseCase

    @Mock
    private lateinit var repo: NewsRepositoryImpl

    @Before
    fun setUp() {
        usecase = GetNewsUseCaseImpl(repo)
    }

    @Test
    fun `should return article data  when api successfully`() {

        val articleResponse = mock(ArticleResponse::class.java)
        `when`(repo.getNewsData("","")).thenReturn(Single.just(articleResponse))

        usecase.getNewsData("","")
            .test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue {
                Assert.assertEquals(articleResponse, it)
                true
            }

        verify(repo).getNewsData("","")
        verifyNoMoreInteractions(repo)

    }

}