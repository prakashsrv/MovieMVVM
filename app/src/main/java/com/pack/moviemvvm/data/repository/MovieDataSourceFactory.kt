package com.pack.moviemvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.vo.MovieResponse
import com.pack.moviemvvm.data.vo.Result
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiservice:TheMovieDBInterface, private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int,Result>() {

    val movieLiveDataSource= MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Result> {


    val movieDataSource=MovieDataSource(apiservice,compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)

        return movieDataSource




     }


}