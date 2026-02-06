package com.ritesh.pathfinder.feature.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.ritesh.pathfinder.feature.screen.drawPathFromNodes
import com.ritesh.pathfinder.feature.viewmodel.PathViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GameBoard(vm: PathViewModel) {
    BoxWithConstraints(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(6.dp)
    ) {
        val gridSize = 15
        val boardSize = constraints.maxWidth.toFloat()
        val tileSize = boardSize / gridSize

        val player = vm.playerPath.last()
        val playerOffset by animateOffsetAsState(
            Offset(player.x * tileSize, player.y * tileSize),
            label = "player"
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color(0xFFBFC9D1))
                .pointerInput(vm.isGameOver) {
                    if (!vm.isGameOver) {
                        detectTapGestures { offset ->
                            val x = (offset.x / tileSize).toInt()
                                .coerceIn(0, gridSize - 1)
                            val y = (offset.y / tileSize).toInt()
                                .coerceIn(0, gridSize - 1)
                            vm.handleTap(x, y)
                        }
                    }
                }
        ) {
            vm.grid.flatten().forEach { node ->
                drawRect(
                    color = when {
                        node.isWall -> Color.Black
                        node.terrainCost == 15 -> Color(0xFF795548)
                        node.terrainCost == 7 -> Color(0xFF2196F3)
                        node.terrainCost == 3 -> Color(0xFF8BC34A)
                        else -> Color(0xFFEEEEEE)
                    },
                    topLeft = Offset(node.x * tileSize, node.y * tileSize),
                    size = Size(tileSize - 1f, tileSize - 1f)
                )
            }
            drawPathFromNodes(
                vm.playerPath,
                tileSize,
                Color.Blue.copy(alpha = 0.5f),
                6f
            )
            vm.optimizedPath?.let {
                drawPathFromNodes(
                    it,
                    tileSize,
                    Color(0xFFFFD700),
                    10f
                )
            }
            drawCircle(
                Color.Red,
                radius = tileSize / 3,
                center = playerOffset + Offset(tileSize / 2, tileSize / 2)
            )
            drawCircle(
                color = Color(0xFFFFC107),
                radius = tileSize / 3,
                center = Offset(
                    (gridSize - 1) * tileSize + tileSize / 2,
                    (gridSize - 1) * tileSize + tileSize / 2
                )
            )
        }
    }
}