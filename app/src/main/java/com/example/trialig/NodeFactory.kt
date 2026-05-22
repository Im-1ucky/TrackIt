package com.example.trialig

object NodeFactory {

    fun createNode(
        amount: Double,
        type: String,
        message: String,
        transactionRef: String?
    ): TransactionNode {

        return TransactionNode(
            amount = amount,
            type = type,
            message = message,
            timestamp = System.currentTimeMillis(),
            transactionRef = transactionRef,
        )
    }
}