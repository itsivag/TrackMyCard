package org.itsivag.trackmycard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.itsivag.cards.di.cardsModule
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

                val config = LocalWindowInfo.current
                val height = config.containerSize.height

                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data("https://raw.githubusercontent.com/itsivag/TrackMyCardPublicData/main/sample.webp")
                            .crossfade(true)
                            .build(),
                        contentDescription = "WebP Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height((height * 0.15).dp)
                    )
                    TransactionsScreen(paddingValues, sampleCards)
                }
            }

        }
    }
}

