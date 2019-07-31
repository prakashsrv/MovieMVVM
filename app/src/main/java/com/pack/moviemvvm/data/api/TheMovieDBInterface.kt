package com.pack.moviemvvm.data.api

import com.pack.moviemvvm.data.vo.MovieDetails
import com.pack.moviemvvm.data.vo.MovieResponse
import com.pack.moviemvvm.data.vo.Result
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {




    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int,@Query("api_key")key:String ): Single<MovieDetails>


    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int,@Query("api_key")key:String ): Single<MovieResponse>
}