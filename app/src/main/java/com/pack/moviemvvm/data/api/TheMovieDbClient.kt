package com.pack.moviemvvm.data.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


 const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
 const val BASE_URL = "https://api.themoviedb.org/3/"
 const val API_KEY = "21642e7bd66fbfce8376d2d3c5b2272c"

const val FIRST_PAGE=1;
const val POST_PER_PAGE=20;


object TheMovieDbClient {


    fun getClient():TheMovieDBInterface
    {

        val requestInterceptor=Interceptor{chain ->


            val url:HttpUrl=chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()





            val request:Request= chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)

        }


        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)



    }



}