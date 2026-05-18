package com.example.trialig

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

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
}