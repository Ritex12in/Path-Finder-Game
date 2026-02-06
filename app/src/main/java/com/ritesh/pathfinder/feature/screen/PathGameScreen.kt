package com.ritesh.pathfinder.feature.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ritesh.pathfinder.feature.components.GameBoard
import com.ritesh.pathfinder.feature.components.GameControls
import com.ritesh.pathfinder.feature.components.GameHUD
import com.ritesh.pathfinder.feature.components.GameInfoBottomSheet
import com.ritesh.pathfinder.feature.model.Node
import com.ritesh.pathfinder.feature.viewmodel.PathViewModel
import kotlinx.coroutines.delay

@Composable
fun PathGameScreen(
    modifier: Modifier = Modifier, vm: PathViewModel = viewModel()
) {
    var showInfo by remember { mutableStateOf(false) }

    LaunchedEffect(vm.isGameOver) {
        while (!vm.isGameOver && vm.timeLeft > 0) {
            delay(1000)
            vm.timeLeft--
            if (vm.timeLeft <= 0) vm.giveUp()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TextButton(
                modifier = Modifier.align(Alignment.End), onClick = {
                    showInfo = true
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "help",
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Help")
                }
            }
            Spacer(Modifier.height(8.dp))
            GameHUD(vm)
            Spacer(Modifier.height(12.dp))
            Text("Start")
            GameBoard(vm)
            Text(
                "End", modifier = Modifier.align(Alignment.End)
            )
            Spacer(Modifier.height(16.dp))
            GameControls(vm)
        }
    }

    if (showInfo) {
        GameInfoBottomSheet { showInfo = false }
    }
}


fun DrawScope.drawPathFromNodes(
    nodes: List<Node>, tileSize: Float, color: Color, strokeWidth: Float
) {
    if (nodes.size < 2) return

    val path = Path().apply {
        moveTo(
            nodes[0].x * tileSize + tileSize / 2, nodes[0].y * tileSize + tileSize / 2
        )
        nodes.forEach {
            lineTo(
                it.x * tileSize + tileSize / 2, it.y * tileSize + tileSize / 2
            )
        }
    }

    drawPath(
        path, color, style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
}




