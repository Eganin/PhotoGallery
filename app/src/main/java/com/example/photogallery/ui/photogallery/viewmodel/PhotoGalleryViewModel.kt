package com.example.photogallery.ui.photogallery.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.photogallery.data.model.entities.GalleryItem
import com.example.photogallery.data.model.repositories.GalleryRepository
import com.example.photogallery.data.storage.QueryPreferences

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {

    val galleryItem: LiveData<List<GalleryItem>>
    private val repository = GalleryRepository()
    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        galleryItem = Transformations.switchMap(mutableSearchTerm) { searchItem ->
            if (searchItem.isBlank()) {
                repository.fetchPhotos()
            } else {
                repository.searchPhotos(query = searchItem)
            }
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(context = app, query = query)
        mutableSearchTerm.value = query
    }

    override fun onCleared() {
        super.onCleared()
        repository.cancelRequest()
    }

}