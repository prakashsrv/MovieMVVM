package com.pack.moviemvvm.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.pack.moviemvvm.R
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.api.TheMovieDbClient
import com.pack.moviemvvm.data.repository.NetworkState
import com.pack.moviemvvm.ui.single_movie_details.SingleMovieViewModel
import com.pack.moviemvvm.ui.single_movie_details.Single_Movie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainActivityViewModel

    lateinit var movirRepository: MoviePageListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: TheMovieDBInterface=TheMovieDbClient.getClient()

        movirRepository= MoviePageListRepository(apiService)

        viewModel=getViewModel()

        val movieAdapter=PopularMoviePageListAdapter(this)

        val gridLayoutManager=GridLayoutManager(this,3)



        gridLayoutManager.spanSizeLookup=object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {

                val viewType:Int=movieAdapter.getItemViewType(position)

                if(viewType==movieAdapter.MOVIE_VIEW_TYPE)return 1 else return 3


            }
        }

        rv_movie_list.layoutManager=gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter=movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

/*
        val intent = Intent(this,Single_Movie::class.java)
        intent.putExtra("id",420818)

        this.startActivity(intent)*/

    }

    private fun getViewModel(): MainActivityViewModel
    {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return MainActivityViewModel(movirRepository) as T

            }

        })[MainActivityViewModel::class.java]
    }

}
