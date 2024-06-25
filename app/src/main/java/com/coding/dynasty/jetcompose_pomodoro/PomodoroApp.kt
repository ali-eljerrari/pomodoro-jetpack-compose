package com.coding.dynasty.jetcompose_pomodoro

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PomodoroApp() {
    var timeLeft by remember { mutableLongStateOf(25 * 60 * 1000L) } // 25 minutes in milliseconds
    var isRunning by remember { mutableStateOf(false) }
    var isWorkPhase by remember { mutableStateOf(true) } // True for work phase, false for break phase
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
             timer = object : CountDownTimer(timeLeft, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = millisUntilFinished
                }

                override fun onFinish() {
                    if (isWorkPhase) {
                        timeLeft = 5 * 60 * 1000L // 5 minutes break
                        isWorkPhase = false
                    } else {
                        timeLeft = 25 * 60 * 1000L // 25 minutes work
                        isWorkPhase = true
                    }
                    isRunning = false
                }
            }
            .start()
        }else{
            timer?.cancel()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = formatTime(timeLeft),
            fontSize = 48.sp,
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = { isRunning = !isRunning }) {
            Text(if (isRunning) "Pause" else if(timeLeft != 25 * 60 * 1000L) "Continue" else "Start")
        }

        Button(onClick = { timeLeft = 25 * 60 * 1000L }, enabled = !isRunning) {
            Text(text = "Reset")
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatTime(timeInMillis: Long): String {
    val minutes = (timeInMillis / 1000) / 60
    val seconds = (timeInMillis / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
