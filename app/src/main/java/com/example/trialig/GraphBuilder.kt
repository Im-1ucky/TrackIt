package com.example.trialig

object GraphBuilder {

    fun buildBalanceGraph(
        transactions: List<TransactionNode>
    ): List<GraphNode> {

        var balance = 0.0

        return transactions.map {

            when(it.type.lowercase()) {

                "root" -> {

                    balance =
                        it.amount
                }

                "credit" -> {

                    balance +=
                        it.amount
                }

                "debit" -> {

                    balance -=
                        it.amount
                }
            }

            GraphNode(

                id = it.id,

                parentId = it.parentId,

                amount = balance,

                type = it.type,

                branchName =
                    it.branchName,

                status =
                    it.status,

                timestamp =
                    it.timestamp,

                depth = 0
            )
        }
    }

    fun buildSpendingGraph(
        transactions: List<TransactionNode>
    ): List<GraphNode> {

        var spent = 0.0

        return transactions
            .filter {

                it.type == "debit"
            }
            .map {

                if (
                    it.type == "debit"
                ) {

                    spent +=
                        it.amount
                }

                GraphNode(

                    id = it.id,

                    parentId = it.parentId,

                    amount = spent,

                    type = it.type,

                    branchName =
                        it.branchName,

                    status =
                        it.status,

                    timestamp =
                        it.timestamp,

                    depth = 0
                )
            }
    }
}