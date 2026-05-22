package com.example.trialig

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TransactionNode::class],
    version = 3
)
abstract class AppDatabase :
    RoomDatabase() {

    abstract fun transactionDao():
            TransactionDao
}