package com.example.photogallery.ui.photogallery.util

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.photogallery.data.PollWorker

private const val TAG = "NotificationReceiver"

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (resultCode != Activity.RESULT_OK) {

        }
        intent?.let {
            val requestCode = intent.getIntExtra(PollWorker.REQUEST_CODE, 0)
            val notification: Notification? = intent.getParcelableExtra(PollWorker.NOTIFICATION)

            val notificationManager = context?.let { NotificationManagerCompat.from(it) }

            notification?.let {
                notificationManager?.notify(
                    requestCode,
                    notification
                )
            }
        }
    }
}