package com.example.hdworld.model

import com.example.hdworld.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class PhotosService {

    @Inject
    lateinit var api: PhotosApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getPhotos(): Single<List<PhotoData>> {
        return api.getPhotos()
    }
}