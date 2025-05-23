package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.itsivag.helper.APP_ICON_URL
import org.itsivag.trackmycard.navigation.NavigationRoutes

@Composable
fun TrackMyCardTopAppBar(currentRoute: String?) {
    when (currentRoute) {
        NavigationRoutes.Home.route -> {
            TopAppBarWithAppIcon()
        }

        NavigationRoutes.Transactions.route -> {}
    }
}

@Composable
private fun TopAppBarWithAppIcon() {
    Box(
        modifier = Modifier.statusBarsPadding().fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = APP_ICON_URL,
            contentDescription = "Track my card",
            modifier = Modifier.padding(16.dp).size(48.dp)
        )
    }
}
