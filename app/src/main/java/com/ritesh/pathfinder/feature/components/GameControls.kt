package com.ritesh.pathfinder.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ritesh.pathfinder.feature.viewmodel.PathViewModel

@Composable
fun GameControls(vm: PathViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(
            onClick = vm::undo,
            shape = RoundedCornerShape(16),
            enabled = vm.undoCharges > 0
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            Spacer(Modifier.width(6.dp))
            Text("Undo")
        }

        FilledTonalButton(
            onClick = vm::giveUp,
            shape = RoundedCornerShape(16),
            enabled = !vm.isGameOver
        ) {
            Text("Give Up")
        }

        Button(
            onClick = vm::resetGame,
            shape = RoundedCornerShape(16),
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(Modifier.width(6.dp))
            Text("Restart")
        }
    }
}