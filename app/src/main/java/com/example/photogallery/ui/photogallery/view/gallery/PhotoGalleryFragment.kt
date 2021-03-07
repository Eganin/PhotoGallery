package com.example.photogallery.ui.photogallery.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.photogallery.R
import com.example.photogallery.data.PollWorker
import com.example.photogallery.ui.photogallery.viewmodel.PhotoGalleryViewModel

class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery) {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoGalleryViewModel =
            ViewModelProvider(this@PhotoGalleryFragment)[PhotoGalleryViewModel::class.java]
        setHasOptionsMenu(true)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val workRequest = OneTimeWorkRequest
            .Builder(PollWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance()
            .enqueue(workRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view = view)
        photoGalleryViewModel.galleryItem.observe(viewLifecycleOwner, {
            recyclerView?.apply {
                adapter = PhotoAdapter(galleryItems = it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragemnt_photo_galley, menu)

        val searchItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    photoGalleryViewModel.fetchPhotos(queryText)
                    return true
                }
                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    return false
                }
            })

            setOnSearchClickListener {
                searchView.setQuery(photoGalleryViewModel.searchTerm,false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_item_clear->{
                photoGalleryViewModel.fetchPhotos(query = "")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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