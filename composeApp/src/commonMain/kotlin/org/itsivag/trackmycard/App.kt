package org.itsivag.trackmycard

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.itsivag.trackmycard.theme.TrackMyCardTheme

@Composable
expect fun isSystemInDarkTheme(): Boolean

@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("Loading") }
    
    LaunchedEffect(Unit) {
        scope.launch {
            text = try {
                Greeting().greeting()
            } catch (e: Exception) {
                e.message ?: "An error occurred"
            }
        }
    }
    
    TrackMyCardTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = text)
            }
        }
    }
}