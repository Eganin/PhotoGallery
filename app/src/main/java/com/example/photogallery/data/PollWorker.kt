package com.example.photogallery.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.photogallery.data.model.repositories.GalleryRepository
import com.example.photogallery.data.storage.QueryPreferences

private const val TAG = "PollWorker"

class PollWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(context = context)
        val lastResultId = QueryPreferences.getLastResultId(context = context)
        val items = if (query.isEmpty()) {
            GalleryRepository()
                .fetchPhotosRequest()
                .execute().body()
                ?.photos
                ?.galleryItem
        } else {
            GalleryRepository()
                .searchPhotosRequest(query = query)
                .execute()
                .body()
                ?.photos
                ?.galleryItem
        } ?: emptyList()

        if(items.isEmpty()){
            return Result.success()
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "Got an old result: $resultId")
        } else {
            Log.i(TAG, "Got a new result: $resultId")
            QueryPreferences.setLastResultId(context=context, lastResultId=resultId)
        }

        return Result.success()
    }

}