package com.example.doubtnut.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.doubtnut.R
import com.example.doubtnut.base.BaseActivity
import com.example.doubtnut.callback.IFragmentChangeCallback
import com.example.doubtnut.ui.fragment.ArticleFragment

class MainActivity : BaseActivity(), IFragmentChangeCallback {

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openArticleFragment()
    }

    private fun openArticleFragment() {
        replaceFragment(R.id.container, ArticleFragment.newInstance(), ArticleFragment.TAG)
    }

    override fun onFragmentChange(fragment: Fragment, tag: String) {
        replaceFragment(R.id.container, fragment, tag, true)
    }
}
