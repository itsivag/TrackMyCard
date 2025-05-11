package org.itsivag.trackmycard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.components.CardPager
import org.itsivag.trackmycard.components.CreditCardInfo
import org.itsivag.trackmycard.components.CustomLinearProgressIndicator
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.onBackgroundColor
import org.itsivag.trackmycard.theme.primaryColor
import org.itsivag.trackmycard.theme.surfaceColor

@Composable
internal fun TransactionsScreen(
    paddingValues: PaddingValues,
    sampleCards: List<CreditCardInfo>
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {
            Text(
                text = "My Cards",
                modifier = Modifier.padding(16.dp),
                fontFamily = OnestFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )
        }
        item {
            CardPager(
                cards = sampleCards,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item {
            CustomLinearProgressIndicator(
                progress = 0.5f,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 32.dp)
            )
        }
        item {
            Text(
                text = "Recent Transactions",
                modifier = Modifier.padding(16.dp),
                fontFamily = OnestFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )
        }

        items(10) {
            Box(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                    .background(color = surfaceColor)
            ) {
                Row {

                    Text(
                        text = "Zomato",
                        fontFamily = DmSansFontFamily(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "1000",
                        fontFamily = DmSansFontFamily(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}


