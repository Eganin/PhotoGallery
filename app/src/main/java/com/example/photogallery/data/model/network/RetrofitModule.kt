package com.example.photogallery.data.model.network

import com.example.photogallery.data.model.network.api.FlickrApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object RetrofitModule {

    private const val BASE_URL = "https://www.flickr.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val api: FlickrApi = retrofit.create()
}