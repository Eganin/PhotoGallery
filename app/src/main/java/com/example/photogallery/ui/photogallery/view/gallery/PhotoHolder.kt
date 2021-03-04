package com.example.photogallery.ui.photogallery.view.gallery


import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery.R

class PhotoHolder(val image : ImageView) : RecyclerView.ViewHolder(image) {

    fun bind(url : String){
        Glide.with(itemView)
            .load(url)
            .centerCrop()
            .fallback(R.drawable.ic_baseline_broken_image_24)
            .into(image)
    }
}