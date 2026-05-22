package com.example.trialig

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var db: AppDatabase? = null

    fun getDatabase(
        context: Context
    ): AppDatabase {

        if (db == null) {

            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "payment_git_history"
            ).fallbackToDestructiveMigration(
                true
            ).build()
        }

        return db!!
    }
}