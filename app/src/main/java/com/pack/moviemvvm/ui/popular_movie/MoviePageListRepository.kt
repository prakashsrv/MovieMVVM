package com.pack.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pack.moviemvvm.data.api.POST_PER_PAGE
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.repository.MovieDataSource
import com.pack.moviemvvm.data.repository.MovieDataSourceFactory
import com.pack.moviemvvm.data.repository.NetworkState
import com.pack.moviemvvm.data.vo.MovieResponse
import com.pack.moviemvvm.data.vo.Result
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiservice:TheMovieDBInterface) {

    lateinit var moviePagedList:LiveData<PagedList<Result>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Result>> {

        movieDataSourceFactory = MovieDataSourceFactory(apiservice, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }


    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState)
    }
}