package com.example.photogallery.application

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.*
import com.example.photogallery.R

const val NOTIFICATION_CHANNEL_ID = "flick_poll"

class PhotoGalleryApplication : Application() {

    private val notificationManager = NotificationManagerCompat.from(applicationContext)

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager.getNotificationChannel(
                NOTIFICATION_CHANNEL_ID
            ) == null
        ) {
            val channel = NotificationChannelCompat.Builder(
                NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
                .setName(getString(R.string.new_pictures_title))
                .setDescription(getString(R.string.new_pictures_text))
                .build()

            notificationManager.createNotificationChannel(channel)
        }
    }
}