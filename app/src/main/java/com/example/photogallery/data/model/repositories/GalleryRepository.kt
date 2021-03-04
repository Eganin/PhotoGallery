package com.example.photogallery.data.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallery.data.model.entities.FlickResponse
import com.example.photogallery.data.model.entities.GalleryItem
import com.example.photogallery.data.model.network.RetrofitModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryRepository {

    private lateinit var call: Call<FlickResponse>

    fun fetchPhotos() = fetchPhotoMetaData(request = RetrofitModule.api.fetchPhotos())

    fun searchPhotos(query: String) =
        fetchPhotoMetaData(request = RetrofitModule.api.searchPhotos(query = query))


    private fun fetchPhotoMetaData(request: Call<FlickResponse>): LiveData<List<GalleryItem>> {
        val responseLiveData = MutableLiveData<List<GalleryItem>>()
        call = request

        call.enqueue(object : Callback<FlickResponse> {
            override fun onResponse(call: Call<FlickResponse>, response: Response<FlickResponse>) {
                val flickResponse = response.body()
                val photoResponse = flickResponse?.photos
                var galleryItems = photoResponse?.galleryItem ?: emptyList()
                galleryItems = galleryItems.filterNot { it.url.isBlank() }
                responseLiveData.value = galleryItems
            }

            override fun onFailure(call: Call<FlickResponse>, t: Throwable) {
                Log.e("NETWORK", "Failed fetch photos")
            }
        })

        return responseLiveData
    }

    fun cancelRequest() {
        if (::call.isInitialized) call.cancel()
    }
}