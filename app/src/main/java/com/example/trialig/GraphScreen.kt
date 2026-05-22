package com.example.trialig

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.runBlocking

@Composable
fun GraphScreen(
    activity: ComponentActivity
) {

    val db =
        DatabaseProvider.getDatabase(
            activity
        )

    val transactions =
        runBlocking {

            db.transactionDao()
                .getAllTransactions()
        }

    val scrollState =
        rememberScrollState()

    val graph =
        GraphBuilder.buildGraph(
            transactions
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                scrollState
            )
    ) {

        TreeCanvas(
            nodes = graph
        )
    }
}