package com.example.photogallery.ui.photogallery.photogallery.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.photogallery.R
import com.example.photogallery.ui.photogallery.util.Router
import com.example.photogallery.ui.photogallery.view.gallery.PhotoGalleryFragment

class PhotoGalleryActivity : AppCompatActivity(), Router {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) openPhotoGallery()
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
        if (addToBackStack) transaction.addToBackStack(fragment::class.simpleName)

        transaction.commit()
    }

    override fun openPhotoGallery() {
        openFragment(fragment = PhotoGalleryFragment.newInstance(), addToBackStack = true)
    }
}