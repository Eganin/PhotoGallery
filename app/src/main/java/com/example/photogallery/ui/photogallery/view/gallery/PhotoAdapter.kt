package com.example.photogallery.ui.photogallery.view.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery.R
import com.example.photogallery.data.model.entities.GalleryItem

class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
    RecyclerView.Adapter<PhotoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoHolder(
            image = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_gallery, parent, false) as ImageView
        )

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) =
        holder.bind(url = galleryItems[position].url)


    override fun getItemCount() = galleryItems.size
}