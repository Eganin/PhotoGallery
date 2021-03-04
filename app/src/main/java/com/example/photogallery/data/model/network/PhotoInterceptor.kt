package com.example.photogallery.data.model.network

import okhttp3.Interceptor
import okhttp3.Response

class PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origRequest = chain.request()

        val newUrl =
            origRequest.url().newBuilder()
                .addQueryParameter("api_key", RetrofitModule.API_KEY)
                .addQueryParameter(RetrofitModule.FORMAT_REQUEST, RetrofitModule.FORMAT)
                .addQueryParameter(
                    RetrofitModule.CALLBACK_REQUEST,
                    RetrofitModule.CALLBACK.toString()
                ).addQueryParameter(RetrofitModule.EXTRAS_REQUEST, RetrofitModule.EXTRAS)
                .build()

        val newRequest = origRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}