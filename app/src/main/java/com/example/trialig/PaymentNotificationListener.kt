package com.example.trialig

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentNotificationListener :
    NotificationListenerService() {

    override fun onNotificationPosted(
        sbn: StatusBarNotification?
    ) {

        if (sbn == null) return

        val packageName = sbn.packageName

        Log.d(
            "NotificationListener",
            "Package: $packageName"
        )

        val extras =
            sbn.notification.extras

        val title =
            extras.getCharSequence("android.title")
                ?.toString()
                ?: ""

        val message =
            extras.getCharSequence("android.text")
                ?.toString()
                ?: ""

        Log.d(
            "NotificationListener",
            "Title: $title"
        )

        Log.d(
            "NotificationListener",
            "Message: $message"
        )

        if (
            NotificationValidator.isValid(
                packageName,
                title,
                message
            )
        ) {

            val node =
                NotificationParser.parse(message)

            if (node != null) {

                Log.d(
                    "TransactionNode",
                    node.toString()
                )

                CoroutineScope(
                    Dispatchers.IO
                ).launch {

                    val db =
                        DatabaseProvider.getDatabase(
                            applicationContext
                        )

                    val existing =
                        db.transactionDao()
                            .getByTransactionRef(
                                node.transactionRef ?: ""
                            )

                    val isDuplicate =
                        existing != null

                    if (!isDuplicate) {

                        db.transactionDao()
                            .insertTransaction(node)

                        Log.d(
                            "Database",
                            "Transaction saved"
                        )
                    }
                    else {

                        Log.d(
                            "Database",
                            "Duplicate skipped"
                        )
                    }

                    val transactions =
                        db.transactionDao()
                            .getAllTransactions()

                    transactions.forEach {

                        Log.d(
                            "Database",
                            it.toString()
                        )
                    }


                }
            }
        }
    }
}