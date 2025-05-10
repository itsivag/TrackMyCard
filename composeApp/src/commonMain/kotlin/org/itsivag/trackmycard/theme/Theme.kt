package org.itsivag.trackmycard.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = primaryColor,
    primaryVariant = Color.Red,
    secondary = Color(0xFF00BCD4),
    background = backgroundColor,
    surface = surfaceColor,
    onPrimary = onPrimaryColor,
    onSecondary = Color.Black,
    onBackground = onBackgroundColor,
    onSurface = onBackgroundColor,
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = Color.Red,
    secondary = Color(0xFF00BCD4),
    background = backgroundColor,
    surface = surfaceColor,
    onPrimary = onPrimaryColor,
    onSecondary = Color.Black,
    onBackground = onBackgroundColor,
    onSurface = onBackgroundColor,
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