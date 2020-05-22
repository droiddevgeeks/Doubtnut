package com.example.doubtnut.ui.fragment

import android.view.View
import com.example.doubtnut.base.BaseFragment
import com.example.doubtnut.model.Article

abstract class AbstractNewsDetailFragment : BaseFragment() {

    companion object {
        val TAG = AbstractNewsDetailFragment::class.java.name
        const val NEWS_DETAIL_DATA = "news_detail_data"
    }

    protected fun getArticleData() = arguments?.getParcelable<Article>(NEWS_DETAIL_DATA)
    override fun viewInitialization(view: View) {}
}