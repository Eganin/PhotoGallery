package com.example.photogallery.data.model.network.api

import com.example.photogallery.data.model.entities.FlickResponse
import com.example.photogallery.data.model.network.RetrofitModule.API_KEY
import com.example.photogallery.data.model.network.RetrofitModule.CALLBACK
import com.example.photogallery.data.model.network.RetrofitModule.CALLBACK_REQUEST
import com.example.photogallery.data.model.network.RetrofitModule.EXTRAS
import com.example.photogallery.data.model.network.RetrofitModule.EXTRAS_REQUEST
import com.example.photogallery.data.model.network.RetrofitModule.FORMAT
import com.example.photogallery.data.model.network.RetrofitModule.FORMAT_REQUEST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/?method=flickr.interestingness.getList")
    fun fetchPhotos(
        @Query("api_key") apiKey: String = API_KEY,
        @Query(FORMAT_REQUEST) format : String = FORMAT,
        @Query(CALLBACK_REQUEST) callback : Int = CALLBACK,
        @Query(EXTRAS_REQUEST) extras : String = EXTRAS
    ): Call<FlickResponse>
}