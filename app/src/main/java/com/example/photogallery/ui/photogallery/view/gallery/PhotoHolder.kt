package com.example.photogallery.ui.photogallery.view.gallery

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhotoHolder(itemView : TextView) : RecyclerView.ViewHolder(itemView) {
    val bindTitle : (CharSequence) -> Unit = itemView::setText
}