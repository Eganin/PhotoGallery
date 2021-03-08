package com.example.photogallery.data

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.photogallery.R
import com.example.photogallery.application.NOTIFICATION_CHANNEL_ID
import com.example.photogallery.data.model.repositories.GalleryRepository
import com.example.photogallery.data.storage.QueryPreferences
import com.example.photogallery.ui.photogallery.photogallery.screens.PhotoGalleryActivity

private const val TAG = "PollWorker"

class PollWorker(private val context: Context, workerParams: WorkerParameters) :
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

        if (items.isEmpty()) {
            return Result.success()
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "Got an old result: $resultId")
        } else {
            Log.i(TAG, "Got a new result: $resultId")
            QueryPreferences.setLastResultId(context = context, lastResultId = resultId)

            val intent = PhotoGalleryActivity.newIntent(context = context)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notification = NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setTicker(context.resources.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(context.resources.getString(R.string.new_pictures_title))
                .setContentText(context.resources.getString(R.string.new_pictures_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(0, notification)

        }
        return Result.success()
    }

}