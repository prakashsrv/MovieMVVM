package com.pack.moviemvvm.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pack.moviemvvm.R
import com.pack.moviemvvm.data.api.BASE_POSTER_PATH
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.api.TheMovieDbClient
import com.pack.moviemvvm.data.repository.NetworkState
import com.pack.moviemvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single__movie.*
import java.text.NumberFormat
import java.util.*

class Single_Movie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel

    private lateinit var movieDetailsRepository: MovieDetailsRepository




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single__movie)

        val movieId= intent.getIntExtra("id",0)

        val apiService:TheMovieDBInterface=TheMovieDbClient.getClient()
        movieDetailsRepository= MovieDetailsRepository(apiService)

        viewModel=getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {


            bindUi(it)

        })


        viewModel.networkState.observe(this, Observer {

            progress_bar.visibility= if (it== NetworkState.LOADING) View.VISIBLE else View.GONE

            txt_error.visibility= if(it== NetworkState.ERROR) View.VISIBLE else View.GONE
        })


    }

    private fun bindUi(it: MovieDetails) {


        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
         movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL:String = BASE_POSTER_PATH + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

    }


    private fun getViewModel(movieId:Int): SingleMovieViewModel
    {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return SingleMovieViewModel(movieDetailsRepository,movieId)as T

            }

        })[SingleMovieViewModel::class.java]
    }
}
