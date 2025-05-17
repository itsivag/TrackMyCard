package org.itsivag.trackmycard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
    addCardShowBottomSheet: Boolean
) {
    val pagerState = rememberPagerState { cards.size }
    val width = LocalWindowInfo.current.containerSize.width

    cards.ifEmpty {
        AddCardButton(modifier,setAddCardShowBottomSheet)
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = (width * 0.02f).dp, end = (width * 0.02f).dp)
    ) { page ->
        if (page == cards.size) {
            Box(modifier.background(color = primaryColor).size(34.dp))
        } else {
            CreditCard(
                card = cards[page],
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                hazeState = hazeState
            )
        }
    }
}

