package org.itsivag.trackmycard.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.itsivag.trackmycard.home.HomeScreen
import org.itsivag.trackmycard.transactions.TransactionsScreen

@Composable
fun TrackMyCardNavHostController(navController: NavHostController, padding: PaddingValues) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(padding, { navController.navigate("transactions") })
        }

        composable("transactions") {
            TransactionsScreen(padding)
        }
    }
}