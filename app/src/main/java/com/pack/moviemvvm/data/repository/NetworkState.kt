package com.pack.moviemvvm.data.repository


enum class Status{

    SUCCESS,RUNNING,FAILURE
}



class NetworkState(val status:Status,val msg:String) {


    companion object{

        val LOADED:NetworkState
        val LOADING:NetworkState
        val ERROR: NetworkState

        val END_OF_LIST: NetworkState


        init {
            LOADED=NetworkState(Status.SUCCESS,"success")

            LOADING=NetworkState(Status.RUNNING,"running")

            ERROR= NetworkState(Status.FAILURE,"failure")


            END_OF_LIST= NetworkState(Status.FAILURE,"end of list")




        }




    }



}