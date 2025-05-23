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
    NavHost(navController, startDestination = NavigationRoutes.Home.route) {
        composable(NavigationRoutes.Home.route) {
            HomeScreen(padding, { navController.navigate(NavigationRoutes.Transactions.route) })
        }

        composable(NavigationRoutes.Transactions.route) {
            TransactionsScreen(padding = padding, navigateBack = { navController.popBackStack() })
        }
    }
}