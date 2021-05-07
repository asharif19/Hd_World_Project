package com.example.hdworld.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hdworld.di.DaggerApiComponent
import com.example.hdworld.model.PhotosService
import com.example.hdworld.model.PhotoData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {

    @Inject
    lateinit var PhotosService : PhotosService

    init {
        DaggerApiComponent.create().inject(this)
    }

    val Photos = MutableLiveData<List<PhotoData>>()
    val PhotoLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        disposable.add(
            PhotosService.getPhotos()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PhotoData>>() {
                    override fun onSuccess(value: List<PhotoData>?) {
                        Photos.value = value
                        PhotoLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        PhotoLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }
}