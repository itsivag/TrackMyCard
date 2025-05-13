package org.itsivag.trackmycard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsivag.helper.DmSansFontFamily
import org.itsivag.trackmycard.theme.secondaryTextColor
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun TransactionListItem(
    title: String,
    description: String,
    amount: Double,
    dateTime: String
) {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(8.dp)
    ) {
        Row {
            Column {
                Text(
                    text = title,
                    fontFamily = DmSansFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        color = secondaryTextColor,
                        fontFamily = DmSansFontFamily(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = dateTime,
                    color = secondaryTextColor,
                    fontFamily = DmSansFontFamily(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "- ₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(amount)}",
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
        }
    }
    Divider(thickness = 1.dp)
}
