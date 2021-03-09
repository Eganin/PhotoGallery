package com.example.photogallery.ui.photogallery.view.gallery


import android.content.Intent
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery.R
import com.example.photogallery.data.model.entities.GalleryItem
import com.example.photogallery.ui.photogallery.photogallery.screens.PhotoPageActivity

class PhotoHolder(val image: ImageView) : RecyclerView.ViewHolder(image) {

    private lateinit var galleryItem: GalleryItem

    init {
        image.setOnClickListener {
            /*
            val intent = PhotoPageActivity.newIntent(
                context = itemView.context,
                photoPageUri = galleryItem.photoPageUri
            )
            itemView.context.startActivity(intent)

             */
            CustomTabsIntent.Builder()
                .setToolbarColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.design_default_color_primary
                    )
                )
                .setShowTitle(true)
                .build()
                .launchUrl(itemView.context, galleryItem.photoPageUri)
        }
    }

    fun bindGalleryItem(item: GalleryItem) {
        galleryItem = item
    }

    fun bind(url: String) {
        Glide.with(itemView)
            .load(url)
            .centerCrop()
            .fallback(R.drawable.ic_baseline_broken_image_24)
            .into(image)
    }
}