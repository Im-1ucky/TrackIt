package com.example.trialig

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import android.util.Log

class MainActivity : ComponentActivity() {
    private var notificationId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val activity = this
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
            Button(
                onClick = {
                    val title = "Union Bank Of India"

                    val message =
                        "A/c *2023 Debited for Rs:448.00 on 02-05-2026 10:57:10 by Mob Bk ref no 123412341234 Avl Bal Rs 123.07. If not you, Call 1800222243 -Union Bank of India"

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                        when {

                            ContextCompat.checkSelfPermission(
                                activity,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED -> {

                                if (NotificationValidator.isValid(packageName, title, message)) {

                                    val node =
                                        NotificationParser.parse(message)

                                    if (node != null) {
                                        Log.d(
                                            "TransactionNode",
                                            node.toString()
                                        )
                                    } else {
                                        Log.d(
                                            "Parser",
                                            "Failed to parse notification"
                                        )
                                    }
                                }

                                sendTestNotification(title, message)
                            }

                            else -> {
                                launcher.launch(
                                    Manifest.permission.POST_NOTIFICATIONS
                                )
                            }
                        }

                    } else {

                        if (NotificationValidator.isValid(packageName,title, message)) {

                            val node =
                                NotificationParser.parse(message)

                            if (node != null) {
                                Log.d(
                                    "TransactionNode",
                                    node.toString()
                                )
                            } else {
                                Log.d(
                                    "Parser",
                                    "Failed to parse notification"
                                )
                            }
                        }

                        sendTestNotification(title, message)
                    }
                }
            ) {
                Text("Send Notification")
            }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun sendTestNotification(title: String, message: String) {


        val channelId = "test_channel"

        val manager =
            getSystemService(NOTIFICATION_SERVICE)
                    as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "Test Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager.createNotificationChannel(channel)
        }

        val notification =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        manager.notify(notificationId++, notification)
    }
}