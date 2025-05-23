package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.itsivag.models.card.CardDataModel
import dev.chrisbanes.haze.HazeState

@Composable
fun CardPager(
    cards: List<CardDataModel>,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    setAddCardShowBottomSheet: (Boolean) -> Unit,
    setCurrentCard: (CardDataModel?) -> Unit,
) {
    val pagerState = rememberPagerState { cards.size + 1 }
    val width = LocalWindowInfo.current.containerSize.width
    val height = LocalWindowInfo.current.containerSize.height

    // Initialize current card and handle page changes
    LaunchedEffect(Unit) {
        // Set initial card
        if (cards.isNotEmpty()) {
            setCurrentCard(cards[0])
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == cards.size) {
            setCurrentCard(null)
        } else {
            setCurrentCard(cards[pagerState.currentPage])
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth().height((height * 0.085f).dp),
        contentPadding = PaddingValues(start = (width * 0.02f).dp, end = (width * 0.02f).dp)
    ) { page ->
        if (page == cards.size) {
            AddCardButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                hazeState = hazeState,
                setAddCardShowBottomSheet = { setAddCardShowBottomSheet(it) })
        } else {
            CreditCard(
                card = cards[page],
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                hazeState = hazeState
            )
        }
    }
}


