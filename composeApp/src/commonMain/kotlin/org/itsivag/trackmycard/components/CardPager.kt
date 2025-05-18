package org.itsivag.trackmycard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.itsivag.cards.model.CardDataModel
import dev.chrisbanes.haze.HazeState
import org.itsivag.trackmycard.theme.primaryColor

@Composable
fun CardPager(
    cards: List<CardDataModel>,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    setAddCardShowBottomSheet: (Boolean) -> Unit,
) {
    val pagerState = rememberPagerState { cards.size + 1 }
    val width = LocalWindowInfo.current.containerSize.width
    val height = LocalWindowInfo.current.containerSize.height

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth().height((height * 0.085f).dp),
        contentPadding = PaddingValues(start = (width * 0.02f).dp, end = (width * 0.02f).dp)
    ) { page ->
        if (page == cards.size) {
            AddCardButton(modifier) { setAddCardShowBottomSheet(it) }
        } else {
            CreditCard(
                card = cards[page],
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                hazeState = hazeState
            )
        }
    }
}


