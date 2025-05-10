package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun CardPager(
    cards: List<CreditCardInfo>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { cards.size }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(end = 32.dp)
    ) { page ->
        CreditCard(
            cardInfo = cards[page],
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
} 