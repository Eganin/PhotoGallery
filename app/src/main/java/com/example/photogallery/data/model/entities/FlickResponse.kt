package com.example.photogallery.data.model.entities

import com.google.gson.annotations.SerializedName

class FlickResponse {
    @SerializedName("photos")
    lateinit var photos: PhotoResponse
}