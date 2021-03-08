package com.example.photogallery.ui.photogallery.view.gallery

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.photogallery.R
import com.example.photogallery.data.PollWorker
import com.example.photogallery.data.storage.QueryPreferences
import com.example.photogallery.ui.photogallery.view.gallery.base.VisibleFragment
import com.example.photogallery.ui.photogallery.viewmodel.PhotoGalleryViewModel
import java.util.concurrent.TimeUnit

class PhotoGalleryFragment : VisibleFragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoGalleryViewModel =
            ViewModelProvider(this@PhotoGalleryFragment)[PhotoGalleryViewModel::class.java]
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

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
                searchView.setQuery(photoGalleryViewModel.searchTerm, false)
            }

            val toggleItem = menu.findItem(R.id.menu_item_toggle_polling)

            val toggleItemTitle = if (QueryPreferences.isPolling(context = requireContext())) {
                R.string.stop_polling
            } else {
                R.string.start_polling
            }

            toggleItem.title = toggleItemTitle.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_clear -> {
                photoGalleryViewModel.fetchPhotos(query = "")
                true
            }
            R.id.menu_item_toggle_polling -> {
                val isPolling = QueryPreferences.isPolling(context = requireContext())
                if (isPolling) {
                    WorkManager.getInstance().cancelUniqueWork(POLL_WORK)
                    QueryPreferences.setPolling(context = requireContext(), isOn = false)
                } else {
                    val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .build()

                    val periodicRequest = PeriodicWorkRequest
                        .Builder(PollWorker::class.java, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()

                    WorkManager.getInstance().enqueueUniquePeriodicWork(
                        POLL_WORK,
                        ExistingPeriodicWorkPolicy.KEEP,
                        periodicRequest
                    )

                    QueryPreferences.setPolling(context = requireContext(), isOn = true)
                }
                activity?.invalidateOptionsMenu()
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
        private const val POLL_WORK = "POLL_WORK"

        fun newInstance() = PhotoGalleryFragment()
    }
}