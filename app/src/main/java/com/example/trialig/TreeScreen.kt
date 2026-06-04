package com.example.trialig

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.runBlocking
import androidx.compose.material3.Surface

@Composable
fun TreeScreen(
    activity: ComponentActivity,
    treeType: TreeType
) {

    val db =
        DatabaseProvider.getDatabase(
            activity
        )

    val transactions =
        runBlocking {

            db.transactionDao()
                .getAllTransactions()
                .reversed()
        }


    val graph =

        when(treeType) {

            TreeType.BALANCE ->

                GraphBuilder
                    .buildBalanceGraph(
                        transactions
                    )

            TreeType.SPENDING ->

                GraphBuilder
                    .buildSpendingGraph(
                        transactions
                    )
        }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        TreeCanvas(
            nodes = graph
        )
    }
}