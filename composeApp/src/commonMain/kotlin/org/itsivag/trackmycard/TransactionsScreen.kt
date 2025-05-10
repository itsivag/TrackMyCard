package org.itsivag.trackmycard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import com.itsivag.helper.OnestFontFamily
import org.itsivag.trackmycard.components.CardPager
import org.itsivag.trackmycard.components.CreditCardInfo
import org.itsivag.trackmycard.theme.backgroundColor
import org.itsivag.trackmycard.theme.onBackgroundColor
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

//        item {
//            Row(modifier = Modifier.padding(16.dp)) {
//                Button(
//                    onClick = {},
//                    shape = RoundedCornerShape(16.dp),
//                    colors = ButtonDefaults.buttonColors(backgroundColor = surfaceColor)
//                ) {
//                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                        Icon(
//                            Icons.Default.Add,
//                            contentDescription = "Add Transaction",
//                            modifier = Modifier.size(48.dp)
//                        )
//                        Text(
//                            text = "Add Transaction",
//                            fontFamily = DmSansFontFamily(),
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//                }
//            }
//        }

        item {
            LinearProgressIndicator(
                progress = 0.7f,
                modifier = Modifier.height(50.dp).fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp)
            )
            Row(modifier = Modifier.padding(16.dp)) {
                Text("Limit utilised: 2000")
                Text("Max Limit: 20000")
            }
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
    }
}