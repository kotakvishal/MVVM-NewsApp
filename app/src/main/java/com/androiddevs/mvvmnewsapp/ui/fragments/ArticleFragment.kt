package com.androiddevs.mvvmnewsapp.ui.fragments

import android.content.ClipData.Item
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.fab
import kotlinx.android.synthetic.main.fragment_article.webView
import kotlinx.android.synthetic.main.fragment_saved_news.rvSavedNews
import java.lang.Exception

class ArticleFragment:Fragment(R.layout.fragment_article) {
    private lateinit var viewModel:NewsViewModel
    private val args:ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        println("DEBUG article fragment called")
        val article = args.article
        webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener {
            try{
                viewModel.saveArticle(article)
                Snackbar.make(view,"Article saved successfully!",Snackbar.LENGTH_SHORT).show()
            }catch (e:Exception){
                println("DEBUG $e")
            }
        }
    }

}