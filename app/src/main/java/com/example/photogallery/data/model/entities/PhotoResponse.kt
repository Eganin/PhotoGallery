package com.example.photogallery.data.model.entities

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItem: List<GalleryItem>
}