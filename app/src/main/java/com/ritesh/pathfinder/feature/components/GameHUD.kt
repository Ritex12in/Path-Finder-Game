package com.ritesh.pathfinder.feature.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ritesh.pathfinder.feature.viewmodel.PathViewModel

@Composable
fun GameHUD(vm: PathViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        ) {
            Column(Modifier.padding(14.dp)) {
                Text(
                    text = if (vm.isGameOver) "SCORE" else "PREDICTED\nMAX SCORE",
                    style = MaterialTheme.typography.labelSmall
                )
                AnimatedContent(vm.currentScore, label = "") {
                    Text(
                        text = if (vm.timeLeft>0)"$it" else "0",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "Undos: ${vm.undoCharges}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }

        TimeBadge(vm.timeLeft)
    }
}