package com.example.trialig

object NotificationParser {

    fun parse(message: String): TransactionNode? {

        val lower = message.lowercase()

        val type =
            when {
                "debited" in lower -> "debit"
                "credited" in lower -> "credit"
                else -> return null
            }

        val refRegex =
            Regex(
                """ref\s*(?:no|number)?\s*(\d+)""",
                RegexOption.IGNORE_CASE
            )

        val amountRegex =
            Regex(
                """(?:rs\.?|rs:|inr|₹)\s*([\d,]+(?:\.\d+)?)""",
                RegexOption.IGNORE_CASE
            )

        val balanceRegex =
            Regex(
                """avl\s*bal\s*rs[:\s]*(\d+(?:\.\d+)?)""",
                RegexOption.IGNORE_CASE
            )

        val availableBalance =
            balanceRegex
                .find(message)
                ?.groupValues
                ?.getOrNull(1)
                ?.toDoubleOrNull()

        val amountMatch =
            amountRegex.find(lower)

        val amount =
            amountMatch
                ?.groupValues
                ?.get(1)
                ?.replace(",", "")
                ?.toDoubleOrNull()
                ?: return null

        val transactionRef =
            refRegex
                .find(message)
                ?.groupValues
                ?.getOrNull(1)

        return NodeFactory.createNode(
            amount = amount,
            type = type,
            message = message,
            transactionRef = transactionRef,
            availableBalance = availableBalance
        )
    }
}