package com.example.trialig

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat

@Composable
fun DebugNotificationScreen(
    activity: ComponentActivity
) {

    val notificationId = remember {
        mutableIntStateOf(1)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(

            onClick = {

                val title =
                    "Union Bank Of India"

                val message =
                    "A/c *2023 Debited for Rs:448.00 on 02-05-2026 10:57:10 by Mob Bk ref no 123412341234 Avl Bal Rs 123.07. If not you, Call 1800222243 -Union Bank of India"

                if (
                    Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.TIRAMISU
                ) {

                    when {

                        ContextCompat.checkSelfPermission(
                            activity,
                            Manifest.permission
                                .POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED -> {

                            sendTestNotification(
                                context = activity,
                                notificationId =
                                    notificationId.intValue++,
                                title = title,
                                message = message
                            )
                        }

                        else -> {

                            launcher.launch(
                                Manifest.permission
                                    .POST_NOTIFICATIONS
                            )
                        }
                    }

                } else {

                    sendTestNotification(
                        context = activity,
                        notificationId =
                            notificationId.intValue++,
                        title = title,
                        message = message
                    )
                }
            }
        ) {

            Text(
                "Send Notification"
            )
        }
    }
}