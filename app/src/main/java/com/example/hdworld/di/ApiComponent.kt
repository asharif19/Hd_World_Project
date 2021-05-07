package com.example.hdworld.di

import com.example.hdworld.model.PhotosService
import com.example.hdworld.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: PhotosService)

    fun inject(viewmodel: ListViewModel)

}