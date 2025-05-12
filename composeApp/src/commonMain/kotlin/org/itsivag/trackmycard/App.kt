package org.itsivag.trackmycard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.transform.Transformation
import com.itsivag.cards.di.cardsModule
import kotlinx.coroutines.launch
import org.itsivag.trackmycard.components.CreditCardInfo
import org.itsivag.trackmycard.components.TopAppBar
import org.itsivag.trackmycard.navigation.TrackMyCardNavHostController
import org.itsivag.trackmycard.theme.TrackMyCardTheme
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin

@Composable
expect fun isSystemInDarkTheme(): Boolean

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("Loading") }
    val navController = rememberNavController()


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
                TrackMyCardNavHostController(navController, paddingValues)
            }

        }
    }
}

