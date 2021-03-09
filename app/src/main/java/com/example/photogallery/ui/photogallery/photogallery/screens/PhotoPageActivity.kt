package com.example.photogallery.ui.photogallery.photogallery.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.R
import com.example.photogallery.ui.photogallery.view.gallery.PhotoPageFragment

class PhotoPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_page)

        intent.data?.let {
            openFragment(data = it)
        }
    }

    private fun openFragment(data: Uri) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = PhotoPageFragment.newInstance(uri = data)

        transaction.replace(R.id.fragment_container_page, fragment).commit()
    }


    companion object {
        fun newIntent(context: Context, photoPageUri: Uri) =
            Intent(context, PhotoPageActivity::class.java).apply {
                data = photoPageUri
            }
    }
}