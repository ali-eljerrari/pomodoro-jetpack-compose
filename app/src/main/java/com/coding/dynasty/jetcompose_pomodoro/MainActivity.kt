package com.coding.dynasty.jetcompose_pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.coding.dynasty.jetcompose_pomodoro.ui.theme.JetcomposepomodoroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetcomposepomodoroTheme {
                PomodoroApp()
            }
        }
    }
}
