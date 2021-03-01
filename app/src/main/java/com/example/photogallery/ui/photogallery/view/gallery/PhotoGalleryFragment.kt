package com.example.photogallery.ui.photogallery.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.R
import com.example.photogallery.data.model.network.RetrofitModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery) {

    private var recyclerView : RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view=view)

        RetrofitModule.api.fetchContents().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG,response.body()!!)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,"network error")
            }
        })
    }

    private fun setupUI(view: View){
        recyclerView = view.findViewById(R.id.photo_recycler_view)
        recyclerView?.apply {
            layoutManager= GridLayoutManager(context,3)
        }
    }



    companion object{
        private const val TAG = "PhotoGalleryFragment"

        fun newInstance()=PhotoGalleryFragment()
    }
}