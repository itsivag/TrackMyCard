package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.itsivag.cards.model.CardDataModel
import dev.chrisbanes.haze.HazeState

@Composable
fun CardPager(
    cards: List<CardDataModel>,
    modifier: Modifier = Modifier,
    hazeState: HazeState
) {
    val pagerState = rememberPagerState { cards.size }
    val width = LocalWindowInfo.current.containerSize.width

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(start = (width * 0.02f).dp, end = (width * 0.02f).dp)
    ) { page ->
        CreditCard(
            card = cards[page],
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            hazeState = hazeState
        )
    }
} 