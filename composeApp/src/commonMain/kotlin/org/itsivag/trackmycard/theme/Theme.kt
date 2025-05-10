package org.itsivag.trackmycard.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = Color(0xFF2196F3),      // Blue
    primaryVariant = Color(0xFF1976D2), // Darker Blue
    secondary = Color(0xFF00BCD4),    // Cyan
    background = Color(0xFF121212),   // Dark Gray
    surface = Color(0xFF1E1E1E),      // Slightly lighter Dark Gray
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF2196F3),      // Blue
    primaryVariant = Color(0xFF1976D2), // Darker Blue
    secondary = Color(0xFF00BCD4),    // Cyan
    background = Color(0xFFF5F5F5),   // Light Gray
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun TrackMyCardTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        content = content
    )
} 