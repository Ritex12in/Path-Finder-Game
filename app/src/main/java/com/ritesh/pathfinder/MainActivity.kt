package com.ritesh.pathfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ritesh.pathfinder.feature.screen.PathGameScreen
import com.ritesh.pathfinder.ui.theme.PathFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PathFinderTheme {
                Scaffold { paddingValues ->
                    PathGameScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}