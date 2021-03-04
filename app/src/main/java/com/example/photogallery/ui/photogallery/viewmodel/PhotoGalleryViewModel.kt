package com.example.photogallery.ui.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery.data.model.entities.GalleryItem
import com.example.photogallery.data.model.repositories.GalleryRepository

class PhotoGalleryViewModel : ViewModel(){

    val galleryItem : LiveData<List<GalleryItem>> = GalleryRepository().searchPhotos("planets")
    private val repository = GalleryRepository()

    override fun onCleared() {
        super.onCleared()
        repository.cancelRequest()
    }

}