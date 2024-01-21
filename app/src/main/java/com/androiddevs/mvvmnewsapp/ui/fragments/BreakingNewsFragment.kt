package com.androiddevs.mvvmnewsapp.ui.fragments

import android.nfc.Tag
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_breaking_news.rvBreakingNews

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news){

    private lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG="BreakingNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            println("DEBUG article fragment called from breaking news")
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response->
        when(response){
            is Resource.Success -> {
                hideProgressBar()
                response.data?.let {
                    newsResponse ->
                    newsAdapter.differ.submitList(newsResponse.articles)
                }
            }
            is Resource.Error ->{
                hideProgressBar()
                response.message?.let {message->
                    println("$TAG error occurred $message")
                }
            }
            is Resource.Loading ->{
                showProgressbar()
            }
        }
        })
    }
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
    private fun hideProgressBar(){
        paginationProgressBar.visibility=View.INVISIBLE
    }
    private fun showProgressbar(){
        paginationProgressBar.visibility=View.VISIBLE
    }
}