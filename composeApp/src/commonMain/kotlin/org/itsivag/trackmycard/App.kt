package org.itsivag.trackmycard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.itsivag.trackmycard.navigation.TrackMyCardNavHostController
import org.itsivag.trackmycard.theme.TrackMyCardTheme
import org.koin.compose.KoinContext

@Composable
expect fun isSystemInDarkTheme(): Boolean


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()
    val navController = rememberNavController()

    TrackMyCardTheme(darkTheme = isDarkTheme) {
        KoinContext {
            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                topBar = {
                    Box(modifier = Modifier.statusBarsPadding())
                },
                modifier = Modifier.fillMaxSize(),
            ) { paddingValues ->
                TrackMyCardNavHostController(navController, paddingValues)
            }

        }
    }
}

