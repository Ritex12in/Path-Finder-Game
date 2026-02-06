package com.ritesh.pathfinder.feature.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TimeBadge(
    time: Int, maxTime: Int = 60
) {
    val progress = time.toFloat() / maxTime.toFloat()
    val danger = time <= 10

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f), animationSpec = tween(500), label = "time_progress"
    )

    Card {
        Box(
            modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                progress = { animatedProgress },
                strokeWidth = 8.dp,
                color = if (danger) Color.Red else Color(0xFF2E7D32),
                strokeCap = StrokeCap.Round,
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$time",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "sec", style = MaterialTheme.typography.labelSmall, color = Color.Gray
                )
            }
        }
    }
}