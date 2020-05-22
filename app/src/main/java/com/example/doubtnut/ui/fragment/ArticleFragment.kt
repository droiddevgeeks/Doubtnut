package com.example.doubtnut.ui.fragment

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubtnut.R
import com.example.doubtnut.callback.IFragmentChangeCallback
import com.example.doubtnut.model.Article
import com.example.doubtnut.ui.adapter.NewsDataAdapter
import kotlinx.android.synthetic.main.article_layout.*

class ArticleFragment : AbstractArticleFragment() {

    companion object {
        val TAG = ArticleFragment::class.java.name
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    private lateinit var fragmentChangeListener: IFragmentChangeCallback
    private val newsData by lazy { ArrayList<Article>() }
    private lateinit var newsAdapter: NewsDataAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentChangeListener = context as IFragmentChangeCallback
    }

    override fun getLayoutRes() = R.layout.article_layout

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
        initAdapter()
    }

    private fun initAdapter() {
        newsAdapter = NewsDataAdapter(newsData,
            handleNewsArticleClick = { article -> onArticleClick(article) })
        with(parent_recycler) {
            this.visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    override fun setIssuesData(list: List<Article>) {
        newsData.addAll(list)
        with(newsAdapter) {
            notifyDataSetChanged()
        }
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading) {
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmerAnimation()
        } else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
        }
    }

    override fun onError(message: String) {
        showToast(message)
    }


    private fun onArticleClick(article: Article) {
        fragmentChangeListener.onFragmentChange(
            NewsDetailFragment.getInstance(article),
            NewsDetailFragment.TAG
        )
    }

}