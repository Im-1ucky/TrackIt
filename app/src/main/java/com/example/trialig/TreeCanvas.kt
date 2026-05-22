package com.example.trialig

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.luminance

@Composable
fun TreeCanvas(
    nodes: List<GraphNode>

) {

    val backgroundColor =
        MaterialTheme.colorScheme.background

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                (nodes.size * 250).dp
            )
    ) {

        val startX = size.width / 2

        val verticalSpacing = 250f

        drawRect(
            color = backgroundColor
        )

        nodes.forEachIndexed { index, node ->


            val y =
                120f + (
                        index * verticalSpacing
                        )

            val isDark =
                backgroundColor.luminance() < 0.5f

            val graphColor =

                if (isDark) {

                    Color.White

                } else {

                    Color.Black
                }

            // DRAW LINE FIRST

            if (index > 0) {

                val previousY =
                    120f + (
                            (index - 1) *
                                    verticalSpacing
                            )

                drawLine(

                    color = graphColor,

                    start = Offset(
                        startX,
                        previousY + 45f
                    ),

                    end = Offset(
                        startX,
                        y - 45f
                    ),

                    strokeWidth = 12f
                )
            }

            // OUTER NODE

            drawCircle(

                color = graphColor.copy(
                    alpha = 0.25f
                ),

                radius = 58f,

                center = Offset(
                    startX,
                    y
                )
            )

            // INNER NODE

            drawCircle(

                color = graphColor,

                radius = 45f,

                center = Offset(
                    startX,
                    y
                )
            )

            // TEXT

            drawContext.canvas.nativeCanvas.drawText(

                "₹${node.amount.toInt()}",

                startX + 90f,

                y + 12f,

                android.graphics.Paint().apply {

                    color =

                        if (isDark) {

                            android.graphics.Color.WHITE

                        } else {

                            android.graphics.Color.BLACK
                        }

                    textSize = 44f

                    isAntiAlias = true
                }
            )
        }
    }
}