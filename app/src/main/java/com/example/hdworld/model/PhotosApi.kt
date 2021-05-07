package com.example.hdworld.model

import io.reactivex.Single
import retrofit2.http.GET

interface PhotosApi {

    var query: String

    @GET("photos/?client_id=wFgDXxW091tf9AzP5FIgF0tRdkblXx7plNcYY5babaQ")
    fun getPhotos(): Single<List<PhotoData>>

    /*@GET("photos?query=$query&client_id=wFgDXxW091tf9AzP5FIgF0tRdkblXx7plNcYY5babaQ")
    fun searchphotos(): Single<List<PhotoData>>*/
}