package com.example.photogallery.ui.photogallery.view.gallery

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.data.model.entities.GalleryItem

class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
    RecyclerView.Adapter<PhotoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoHolder(itemView = TextView(parent.context))

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bindTitle(galleryItems[position].title)
    }

    override fun getItemCount() = galleryItems.size
}