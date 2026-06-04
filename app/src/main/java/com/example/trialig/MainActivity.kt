package com.example.trialig

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.trialig.ui.theme.TrialigTheme
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            TrialigTheme {

                val rootExists =
                    runBlocking {

                        DatabaseProvider
                            .getDatabase(
                                this@MainActivity
                            )
                            .transactionDao()
                            .getRootNode() != null
                    }

                var setupComplete by remember {
                    mutableStateOf(false)
                }

                if (rootExists || setupComplete) {

                    val pagerState =
                        rememberPagerState(
                            pageCount = { 2 }
                        )

                    HorizontalPager(
                        state = pagerState
                    ) { page ->

                        when(page) {

                            0 -> {

                                DebugNotificationScreen(
                                    activity = this@MainActivity
                                )
                            }

                            1 -> {

                                GraphScreen(
                                    activity = this@MainActivity
                                )
                            }
                        }
                    }

                } else {

                    SetupScreen(
                        activity = this@MainActivity,
                        onContinue = {
                            setupComplete = true
                        }
                    )
                }
            }
        }
    }
}