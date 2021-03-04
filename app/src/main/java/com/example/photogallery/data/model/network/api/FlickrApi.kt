package com.example.photogallery.data.model.network.api

import com.example.photogallery.data.model.entities.FlickResponse
import com.example.photogallery.data.model.network.RetrofitModule.API_KEY
import com.example.photogallery.data.model.network.RetrofitModule.CALLBACK
import com.example.photogallery.data.model.network.RetrofitModule.CALLBACK_REQUEST
import com.example.photogallery.data.model.network.RetrofitModule.EXTRAS
import com.example.photogallery.data.model.network.RetrofitModule.EXTRAS_REQUEST
import com.example.photogallery.data.model.network.RetrofitModule.FORMAT
import com.example.photogallery.data.model.network.RetrofitModule.FORMAT_REQUEST
import com.example.photogallery.data.model.network.RetrofitModule.TEXT_REQUEST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/?method=flickr.interestingness.getList")
    fun fetchPhotos(): Call<FlickResponse>

    @GET("services/rest?method=flickr.photos.search")
    fun searchPhotos(@Query(TEXT_REQUEST) query: String): Call<FlickResponse>
}