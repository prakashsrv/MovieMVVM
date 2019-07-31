package com.pack.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.pack.moviemvvm.data.repository.NetworkState
import com.pack.moviemvvm.data.vo.Result
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val moviePageListRepository: MoviePageListRepository):ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Result>> by lazy {
        moviePageListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        moviePageListRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}