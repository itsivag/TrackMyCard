package org.itsivag.trackmycard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itsivag.cards.di.cardsModule
import org.itsivag.trackmycard.components.CardPager
import org.itsivag.trackmycard.components.CreditCardInfo
import org.itsivag.trackmycard.components.TopAppBar
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

    val sampleCards = remember {
        listOf(
            CreditCardInfo(
                cardNumber = "4532 1234 5678 9012",
                cardHolderName = "JOHN DOE",
                expiryDate = "12/25",
                cardType = "VISA",
                gradientColors = listOf(Color(0xFF1A237E), Color(0xFF3949AB))
            ),
            CreditCardInfo(
                cardNumber = "5123 4567 8901 2345",
                cardHolderName = "JANE SMITH",
                expiryDate = "09/24",
                cardType = "MASTERCARD",
                gradientColors = listOf(Color(0xFFB71C1C), Color(0xFFD32F2F))
            ),
            CreditCardInfo(
                cardNumber = "3782 8224 6310 0055",
                cardHolderName = "MIKE JOHNSON",
                expiryDate = "03/26",
                cardType = "AMEX",
                gradientColors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))
            )
        )
    }

    startKoin {
        modules(cardsModule)
    }

    TrackMyCardTheme(darkTheme = isDarkTheme) {
        KoinContext {
            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                topBar = {
                    TopAppBar(
                        title = "My Cards",
                        modifier = Modifier.statusBarsPadding()
                    )
                },
                modifier = Modifier.fillMaxSize(),
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    CardPager(
                        cards = sampleCards,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}