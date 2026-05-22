package com.example.trialig

object GraphBuilder {

    fun buildGraph(
        transactions: List<TransactionNode>
    ): List<GraphNode> {

        return transactions.map {

            GraphNode(
                id = it.id,
                parentId = it.parentId,
                amount = it.amount,
                type = it.type,
                branchName = it.branchName,
                status = it.status,
                depth = 0
            )
        }
    }
}