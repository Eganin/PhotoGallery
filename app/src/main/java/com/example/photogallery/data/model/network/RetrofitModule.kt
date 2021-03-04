package com.example.photogallery.data.model.network

import com.example.photogallery.data.model.network.api.FlickrApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object RetrofitModule {

    private const val BASE_URL = "https://api.flickr.com/"
    const val API_KEY = "514ee23b0a9f7e4a74021a39cb029fc0"
    const val FORMAT_REQUEST = "format"
    const val FORMAT = "json"
    const val EXTRAS_REQUEST = "extras"
    const val EXTRAS = "url_s"
    const val CALLBACK_REQUEST = "nojsoncallback"
    const val CALLBACK = 1
    const val TEXT_REQUEST = "text"

    private val client = OkHttpClient.Builder()
        .addInterceptor(PhotoInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: FlickrApi = retrofit.create()
}