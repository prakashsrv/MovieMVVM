package com.pack.moviemvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.pack.moviemvvm.data.api.FIRST_PAGE
import com.pack.moviemvvm.data.api.TheMovieDBInterface
import com.pack.moviemvvm.data.vo.MovieResponse
import com.pack.moviemvvm.data.vo.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private  val apiService:TheMovieDBInterface,private  val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int,Result>() {

        private var page = FIRST_PAGE

        val networkState:MutableLiveData<NetworkState> = MutableLiveData()



    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(apiService.getPopularMovie(page,"21642e7bd66fbfce8376d2d3c5b2272c").subscribeOn(Schedulers.io()).subscribe(

            {
                callback.onResult(it.results,null,page+1)
                networkState.postValue(NetworkState.LOADED)
            },
            {
                networkState.postValue(NetworkState.ERROR)
            }
        ))

     }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(apiService.getPopularMovie(params.key,"21642e7bd66fbfce8376d2d3c5b2272c").subscribeOn(Schedulers.io()).subscribe(

            {
                if(it.totalPages >= params.key)
                {
                    callback.onResult(it.results,page+1)
                }
                else
                {
                    networkState.postValue(NetworkState.END_OF_LIST)
                }


            },
            {
                networkState.postValue(NetworkState.ERROR)
            }
        ))


     }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
     }


}