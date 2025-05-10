package org.itsivag.trackmycard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.cards.di.cardsModule
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.theme.TrackMyCardTheme
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin

@Composable
expect fun isSystemInDarkTheme(): Boolean

@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("Loading") }

    startKoin {
        modules(cardsModule)
    }

    TrackMyCardTheme(darkTheme = isDarkTheme) {
        KoinContext {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
            ) {

            }
        }
    }
}

