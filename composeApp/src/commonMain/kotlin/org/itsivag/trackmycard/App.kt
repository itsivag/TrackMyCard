package org.itsivag.trackmycard

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.itsivag.trackmycard.components.TrackMyCardTopAppBar
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
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }

    TrackMyCardTheme(darkTheme = isDarkTheme) {
        KoinContext {
            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                topBar = {
                    TrackMyCardTopAppBar(currentRoute = currentRoute)
                },
                modifier = Modifier.fillMaxSize(),
            ) { paddingValues ->
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {},
                    state = pullToRefreshState,
                ) {
                    TrackMyCardNavHostController(navController, paddingValues)
                }
            }

        }
    }
}


