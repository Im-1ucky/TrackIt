package com.example.trialig

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionNode(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,

    val type: String,

    val message: String,

    val timestamp: Long,
    val parentId: Int? = null,
    val branchName: String = "main",
    val status: String = "REGULAR",
    val transactionRef: String? = null
)