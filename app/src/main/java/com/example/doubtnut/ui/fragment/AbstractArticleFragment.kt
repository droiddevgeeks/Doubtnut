package com.example.doubtnut.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.doubtnut.base.ApplicationConstants
import com.example.doubtnut.base.BaseFragment
import com.example.doubtnut.base.CustomViewModelFactory
import com.example.doubtnut.common.EventObserver
import com.example.doubtnut.model.Article
import com.example.doubtnut.ui.viewmodel.MainViewModel
import javax.inject.Inject

abstract class AbstractArticleFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchNewsData(ApplicationConstants.COUNTRY)
    }

    override fun viewInitialization(view: View) {
        observeDataChange()
    }


    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.articleLiveData.observe(viewLifecycleOwner, EventObserver {
            setIssuesData(it)
        })
    }


    abstract fun setIssuesData(list: List<Article>)

}