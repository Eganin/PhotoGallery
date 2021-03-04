package com.example.photogallery.ui.photogallery.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.R
import com.example.photogallery.data.model.repositories.GalleryRepository
import com.example.photogallery.ui.photogallery.viewmodel.PhotoGalleryViewModel

class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery) {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoGalleryViewModel =
            ViewModelProviders.of(this@PhotoGalleryFragment).get(PhotoGalleryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view = view)
        photoGalleryViewModel.galleryItem.observe(viewLifecycleOwner,{
            recyclerView?.apply {
                adapter= PhotoAdapter(galleryItems = it)
            }
        })
    }

    private fun setupUI(view: View) {
        recyclerView = view.findViewById(R.id.photo_recycler_view)
        recyclerView?.apply {
            layoutManager = GridLayoutManager(context, 3)
        }
    }


    companion object {
        private const val TAG = "PhotoGalleryFragment"

        fun newInstance() = PhotoGalleryFragment()
    }
}