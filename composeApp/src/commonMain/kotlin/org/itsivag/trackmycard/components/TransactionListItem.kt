package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.itsivag.helper.DmSansFontFamily
import org.itsivag.trackmycard.theme.secondaryTextColor

@Composable
internal fun TransactionListItem() {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(8.dp)
    ) {
        Row {
            Column {
                Text(
                    text = "Zomato",
                    fontFamily = DmSansFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    "17 Nov, 5:34PM",
                    color = secondaryTextColor,
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "- ₹258",
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
        }

    }
    Divider(thickness = 1.dp)
}
