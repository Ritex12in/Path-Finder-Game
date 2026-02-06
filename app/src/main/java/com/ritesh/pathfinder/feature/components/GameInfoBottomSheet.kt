package com.ritesh.pathfinder.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameInfoBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "Terrain Intelligence",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TerrainItem("Road", 1, Color(0xFFEEEEEE))
                TerrainItem("Mud", 3, Color(0xFF8BC34A))
                TerrainItem("Water", 7, Color(0xFF2196F3))
                TerrainItem("Peak", 15, Color(0xFF795548))
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Score = 100 − (Your Cost − Optimal Cost)",
                color = Color.Gray
            )
        }
    }
}