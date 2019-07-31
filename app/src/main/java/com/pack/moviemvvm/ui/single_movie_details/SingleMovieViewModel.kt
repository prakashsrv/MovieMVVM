package com.pack.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pack.moviemvvm.data.repository.NetworkState
import com.pack.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository,movieId:Int) : ViewModel() {

    private val compositeDisposable=CompositeDisposable()


    val movieDetails:LiveData<MovieDetails> by lazy {

        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {

        movieDetailsRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}