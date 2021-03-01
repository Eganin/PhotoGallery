package com.example.photogallery.data.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallery.data.model.network.RetrofitModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryRepository {
    fun fetchContents() : LiveData<String>{
        val responseLiveData = MutableLiveData<String>()
        val request = RetrofitModule.api.fetchContents()

        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("NETWORK","Failed fetch photos")
            }
        })

        return responseLiveData
    }
}