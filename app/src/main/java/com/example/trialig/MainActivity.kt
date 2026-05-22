package com.example.trialig

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.trialig.ui.theme.TrialigTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            TrialigTheme {

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
            }
        }
    }
}