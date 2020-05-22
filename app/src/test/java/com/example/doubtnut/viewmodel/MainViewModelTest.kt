package com.example.doubtnut.viewmodel

import com.example.doubtnut.testutil.Rx2SchedulersOverrideRule
import com.example.doubtnut.usecase.GetNewsUseCase
import io.reactivex.Single
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.doubtnut.common.Event
import com.example.doubtnut.model.Article
import com.example.doubtnut.model.ArticleResponse
import com.example.doubtnut.ui.viewmodel.MainViewModel
import com.jraska.livedata.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @JvmField
    @Rule
    val rx2SchedulersOverrideRule = Rx2SchedulersOverrideRule()

    @JvmField
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var newUseCase: GetNewsUseCase

    private lateinit var viewModel: MainViewModel
    private lateinit var loadingJraskaTestObserver: TestObserver<Boolean>
    private lateinit var loadingJraskaTestStates: List<Boolean>


    @Mock
    lateinit var dataObserver: Observer<Event<List<Article>>>
    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var errorObserver: Observer<Event<Throwable>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(newUseCase)
    }


    @Test
    fun `should return article data when fetching news data using Jraska Library way`() {
        //Assemble
        val articleResponse = ArticleResponse("",1, arrayListOf())
        `when`(newUseCase.getNewsData("", "")).thenReturn(Single.just(articleResponse))

        loadingJraskaTestObserver = TestObserver.test(viewModel.loadingState)
        loadingJraskaTestStates = loadingJraskaTestObserver.valueHistory()

        val dataObserver = TestObserver.test(viewModel.articleLiveData)

        //Act
        viewModel.fetchNewsData("", "")

        //Assert
        loadingJraskaTestObserver.assertHistorySize(2)
        assertEquals(true, loadingJraskaTestStates[0])
        assertEquals(false, loadingJraskaTestStates[1])
        assertEquals(dataObserver.value().getContentIfNotHandled(), articleResponse.articles)
    }

    @Test
    fun `should return error when fetching News data using Jraska Library way`() {
        //Assemble
        val throwable = Throwable("Something went wrong")
        `when`(newUseCase.getNewsData("","")).thenReturn(Single.error(throwable))

        loadingJraskaTestObserver = TestObserver.test(viewModel.loadingState)
        loadingJraskaTestStates = loadingJraskaTestObserver.valueHistory()

        val errorObserver = TestObserver.test(viewModel.apiError)

        //Act
        viewModel.fetchNewsData("", "")

        //Assert
        loadingJraskaTestObserver.assertHistorySize(3)
        assertEquals(true, loadingJraskaTestStates[0])
        assertEquals(false, loadingJraskaTestStates[1])
        assertEquals(false, loadingJraskaTestStates[2])
        assertEquals(errorObserver.value().getContentIfNotHandled(), throwable)
    }

    @Test
    fun `should return news data when fetching news data using observer way`() {
        //Assemble
        val articleList = listOf<Article>()
        val articleResponse = ArticleResponse("pass", 20, articleList)
        `when`(newUseCase.getNewsData("", "")).thenReturn(Single.just(articleResponse))

        viewModel.articleLiveData.observeForever(dataObserver)
        viewModel.loadingState.observeForever(loadingObserver)
        viewModel.apiError.observeForever(errorObserver)

        //Act
        viewModel.fetchNewsData("","")

        //Assert
        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
        assertEquals(viewModel.articleLiveData.value?.getContentIfNotHandled(), articleResponse.articles)
        assertEquals(viewModel.apiError.value?.getContentIfNotHandled(), null)
    }

    @Test
    fun `should return error when fetching article list issue using observer way`() {
        //Assemble
        val throwable = Throwable("Something went wrong")
        `when`(newUseCase.getNewsData("","")).thenReturn(Single.error(throwable))

        viewModel.articleLiveData.observeForever(dataObserver)
        viewModel.loadingState.observeForever(loadingObserver)
        viewModel.apiError.observeForever(errorObserver)

        //Act
        viewModel.fetchNewsData("","")

        //Assert
        verify(loadingObserver).onChanged(true)
        assertEquals(viewModel.articleLiveData.value?.getContentIfNotHandled(), null)
        assertEquals(viewModel.apiError.value?.getContentIfNotHandled(), throwable)
    }
}