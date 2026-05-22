package com.example.trialig


import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.Surface

@Composable
fun DebugNotificationScreen(
    activity: ComponentActivity
) {

    val notificationId = remember {
        mutableIntStateOf(1)
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(

            modifier = Modifier.fillMaxSize(),

            verticalArrangement =
                Arrangement.Center,

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            fun buildMessage(
                type: String
            ): String {

                val ref =
                    (100000000000..999999999999)
                        .random()

                val time =
                    System.currentTimeMillis()

                return if (
                    type == "credit"
                ) {

                    "A/c *2023 Credited for Rs:448.00 " +
                            "on $time by Mob Bk ref no $ref " +
                            "Avl Bal Rs 123.07. " +
                            "-Union Bank of India"

                } else {

                    "A/c *2023 Debited for Rs:448.00 " +
                            "on $time by Mob Bk ref no $ref " +
                            "Avl Bal Rs 123.07. " +
                            "-Union Bank of India"
                }
            }

            Button(

                onClick = {

                    val title =
                        "Union Bank Of India"

                    val message =
                        buildMessage(
                            "debit"
                        )

                    sendTestNotification(
                        context = activity,
                        notificationId =
                            notificationId.intValue++,
                        title = title,
                        message = message
                    )
                }
            ) {

                Text(
                    "Send Debit"
                )
            }

            Spacer(
                modifier = Modifier.height(
                    24.dp
                )
            )

            Button(

                onClick = {

                    val title =
                        "Union Bank Of India"

                    val message =
                        buildMessage(
                            "credit"
                        )

                    sendTestNotification(
                        context = activity,
                        notificationId =
                            notificationId.intValue++,
                        title = title,
                        message = message
                    )
                }
            ) {

                Text(
                    "Send Credit"
                )
            }

            Spacer(
                modifier = Modifier.height(
                    24.dp
                )
            )

            Button(

                onClick = {

                    CoroutineScope(
                        Dispatchers.IO
                    ).launch {

                        DatabaseProvider
                            .getDatabase(activity)
                            .transactionDao()
                            .deleteAllTransactions()

                        Log.d(
                            "Database",
                            "All transactions deleted"
                        )
                    }
                }
            ) {

                Text(
                    "Delete All Nodes"
                )
            }
        }
    }
}