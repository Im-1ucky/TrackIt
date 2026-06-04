package com.example.trialig

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.luminance

@Composable
fun TreeCanvas(
    nodes: List<GraphNode>
) {
    val backgroundColor = MaterialTheme.colorScheme.background

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                maxOf(
                    1000,
                    nodes.size * 250
                ).dp
            )
    ) {
        val startX = size.width / 2
        val isDark = backgroundColor.luminance() < 0.5f
        val graphColor = if (isDark) Color.White else Color.Black
        val verticalSpacing = 250f

        drawRect(color = backgroundColor)


        val latestIndex =
            nodes.lastIndex

        val centerY =
            size.height / 2f

        nodes.forEachIndexed { index, node ->
            val y =
                centerY +
                        (index - latestIndex) *
                        verticalSpacing

            Log.d("TREE_NODE", "index=$index y=$y amount=${node.amount}")

            // line between nodes
            if (index > 0) {
                val nodeRadius = 45f

                val previousY =
                    centerY +
                            ((index - 1) - latestIndex) *
                            verticalSpacing

                drawLine(
                    color = graphColor,
                    start = Offset(
                        startX,
                        previousY + nodeRadius
                    ),
                    end = Offset(
                        startX,
                        y - nodeRadius
                    ),
                    strokeWidth = 6f
                )
            }

            // outer node
            drawCircle(
                color = graphColor.copy(alpha = 0.25f),
                radius = 58f,
                center = Offset(startX, y)
            )

            // inner node
            drawCircle(
                color = graphColor,
                radius = 45f,
                center = Offset(startX, y)
            )

            // amount text
            drawContext.canvas.nativeCanvas.drawText(
                "₹${node.amount.toInt()}",
                startX + 90f,
                y - 8f,
                android.graphics.Paint().apply {
                    color = if (isDark) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                    textSize = 44f
                    isAntiAlias = true
                }
            )

            // date text
            drawContext.canvas.nativeCanvas.drawText(
                DateFormatter.format(node.timestamp),
                startX + 90f,
                y + 38f,
                android.graphics.Paint().apply {
                    color = if (isDark) android.graphics.Color.LTGRAY else android.graphics.Color.DKGRAY
                    textSize = 28f
                    isAntiAlias = true
                }
            )
        }
    }
}