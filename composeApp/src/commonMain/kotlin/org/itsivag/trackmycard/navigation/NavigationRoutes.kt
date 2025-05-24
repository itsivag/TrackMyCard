package org.itsivag.trackmycard.navigation
 
sealed class NavigationRoutes(val route: String) {
    object Home : NavigationRoutes("home")
    object Transactions : NavigationRoutes("transactions")
} 