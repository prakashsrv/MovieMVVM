package com.pack.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.vo.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDeailsNetworkDataSource(private val apiService:TheMovieDBInterface,private val compositeDisposable: CompositeDisposable ) {


private val _networkState=MutableLiveData<NetworkState>()


    val networkState: LiveData<NetworkState>

        get()= _networkState


    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()


    val downloadedMovieDetailsResponse:LiveData<MovieDetails>

        get()=_downloadedMovieDetailsResponse



    fun fetchMovieDetails(movieId: Int)
    {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(apiService.getMovieDetails(movieId,"21642e7bd66fbfce8376d2d3c5b2272c")
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _downloadedMovieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)


                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)

                    }
                ))

        }
        catch (ex:Exception)
        {
                Log.e("error",ex.message)
         }







    }



}

