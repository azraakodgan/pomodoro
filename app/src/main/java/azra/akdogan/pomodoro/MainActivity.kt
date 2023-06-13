package azra.akdogan.pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import azra.akdogan.pomodoro.ui.theme.AppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PomodoroApp()
            }
        }
    }
}

@Composable
fun PomodoroApp(modifier: Modifier = Modifier) {
    var timeRemaining by remember { mutableStateOf(25 * 60) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeRemaining > 0) {
                delay(1000)
                timeRemaining--
            }
        }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomComponent(
            maxIndicatorValue = 25 * 60,
            indicatorValue = ((25 * 60) - timeRemaining),
            displayText = formatTime(timeRemaining),
            bigTextFontSize = MaterialTheme.typography.headlineLarge.fontSize,
            foregroundIndicatorStrokeWidth = 80.0f
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { isRunning = !isRunning }) {
                if (isRunning)
                    Icon(Icons.Rounded.Close, contentDescription = null, Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onBackground)
                else
                    Icon(Icons.Rounded.PlayArrow, contentDescription = null, Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onBackground)
            }
            IconButton(onClick = { isRunning = false; timeRemaining = 25 * 60 }) {
                Icon(Icons.Sharp.Refresh, contentDescription = null, Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PomodoroPreview() {
    AppTheme() {
        PomodoroApp()
    }
}

fun formatTime(timeInSeconds: Int): String {
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}