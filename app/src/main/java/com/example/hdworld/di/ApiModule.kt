package com.example.hdworld.di

import com.example.hdworld.model.PhotosApi
import com.example.hdworld.model.PhotosService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://api.unsplash.com"

    @Provides
    fun provideCountriesApi(): PhotosApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PhotosApi::class.java)
    }

    @Provides
    fun provideCountriesService(): PhotosService {
        return PhotosService()
    }

}