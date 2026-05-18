package com.example.trialig

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(
        transaction: TransactionNode
    )

    @Query(
        "SELECT * FROM transactions ORDER BY timestamp DESC"
    )
    suspend fun getAllTransactions():
            List<TransactionNode>

    @Delete
    suspend fun deleteTransaction(
        transaction: TransactionNode
    )

    @Query(
        "SELECT * FROM transactions " +
                "WHERE message = :message " +
                "ORDER BY timestamp DESC " +
                "LIMIT 1"
    )
    suspend fun getLatestByMessage(
        message: String
    ): TransactionNode?
}