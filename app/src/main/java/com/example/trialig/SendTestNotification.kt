package com.example.trialig

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

@Suppress("DEPRECATION")
fun sendTestNotification(
    context: Context,
    notificationId: Int,
    title: String,
    message: String
) {

    val channelId = "test_channel"

    val manager =
        context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channel = NotificationChannel(
            channelId,
            "Test Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannel(channel)
    }

    val notification =
        NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(
                android.R.drawable.ic_dialog_info
            )
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(
                NotificationCompat.PRIORITY_DEFAULT
            )
            .build()

    manager.notify(
        notificationId,
        notification
    )
}