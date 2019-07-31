package com.pack.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.repository.MovieDeailsNetworkDataSource
import com.pack.moviemvvm.data.repository.NetworkState
import com.pack.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiservice:TheMovieDBInterface) {


    lateinit var movieDetailsNetworkSource:MovieDeailsNetworkDataSource



    fun fetchSingleMovieDetails(compositeisposable:CompositeDisposable,movieId:Int): LiveData<MovieDetails>
    {

        movieDetailsNetworkSource= MovieDeailsNetworkDataSource(apiservice,compositeisposable)
        movieDetailsNetworkSource.fetchMovieDetails(movieId)


        return movieDetailsNetworkSource.downloadedMovieDetailsResponse

    }


    fun getMovieDetailNetworkState():LiveData<NetworkState>
    {

        return movieDetailsNetworkSource.networkState



    }



}